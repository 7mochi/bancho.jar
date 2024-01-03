package pe.nanamochi.osu.banchojar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pe.nanamochi.osu.banchojar.entities.Gamemode;
import pe.nanamochi.osu.banchojar.entities.Geolocation;
import pe.nanamochi.osu.banchojar.entities.db.Session;
import pe.nanamochi.osu.banchojar.entities.db.User;
import pe.nanamochi.osu.banchojar.packets.io.ByteDataOutputStream;
import pe.nanamochi.osu.banchojar.packets.server.*;
import pe.nanamochi.osu.banchojar.service.SessionService;
import pe.nanamochi.osu.banchojar.service.UserService;
import pe.nanamochi.osu.banchojar.utils.IPApi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

@RestController
@RequestMapping("/bancho")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class OsuController {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    /**
     * User: nanamochi-
     * Password: 098f6bcd4621d373cade4e832627b4f6 (test)
     * Client data: b20230814.2|-5|0|28ed552233d877a3d936a57dc588c95f:.D8BBC1184647.:d761dcbd931147a0875db461cf1a47f5:5d6a9fcd31f3a5e8c2c5706fe5ab6d19:4a48ececac51ef79cecea9fdb4873eec:|1
     * <p>
     * osu_version|utc_offset|display_city|client_hashes|pm_private
     */
    @PostMapping(value = "/", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> banchoHandler(@RequestHeader MultiValueMap<String, String> headers, @RequestBody String data) throws IOException {
        ResponseEntity<Resource> response = null;
        if (!headers.containsKey("osu-token")) {
            response = handleLogin(headers, data);
        } else {
            response = handleBanchoRequest(headers, data);
        }
        return response;
    }

    private ResponseEntity<Resource> handleLogin(MultiValueMap<String, String> headers, String data) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        System.out.println(headers);

        String[] splitedData = data.split("\n", 3);
        String username = splitedData[0];
        String password = splitedData[1];
        String clientInfo = splitedData[2];

        String[] splitedClientInfo = clientInfo.split("\\|", 5);
        String osuVersion = splitedClientInfo[0];
        String utcOffset = splitedClientInfo[1];
        String displayCity = splitedClientInfo[2];
        String clientHashes = splitedClientInfo[3];
        String pmPrivate = splitedClientInfo[4];

        String[] splitedClientHashes = clientHashes.split(":", 5);
        String osuPathMd5 = splitedClientHashes[0];
        String adaptersStr = splitedClientHashes[1];
        String adaptersMd5 = splitedClientHashes[2];
        String uninstallMd5 = splitedClientHashes[3];
        String diskSignatureMd5 = splitedClientHashes[4];

        InetAddress ipAddress = null;
        if (!headers.containsKey("x-real-ip")) {
            User user = new User();
            user.sendPacket(new PacketUserID(-1));
            user.sendPacket(new PacketNotification("Could not determine your IP address."));

            return processPackets(user, "no");
        } else {
            ipAddress = InetAddress.getByName(headers.getFirst("x-real-ip"));
        }

        Geolocation geoloc = null;
        if (ipAddress.isAnyLocalAddress() || ipAddress.isLoopbackAddress()) {
            geoloc = new Geolocation();
            geoloc.setLat(0.0f);
            geoloc.setLon(0.0f);
        } else {
            geoloc = IPApi.fetchFromIP(ipAddress);
        }

        User user = userService.login(username, password);

        if (user != null) {
            user.sendPacket(new PacketProtocolVersion(user.getProtocolVersion()));
            user.sendPacket(new PacketUserID(user.getId()));
            user.sendPacket(new PacketPrivileges(0));

            Session session = new Session();
            session.setUser(user);
            session.setOsuVersion(osuVersion);
            session.setUtcOffset(Integer.parseInt(utcOffset));
            session.setDisplayCityLocation(Boolean.parseBoolean(displayCity));
            session.setPmPrivate(Boolean.parseBoolean(pmPrivate));
            session.setOsuPathMd5(osuPathMd5);
            session.setAdaptersStr(adaptersStr);
            session.setAdaptersMd5(adaptersMd5);
            session.setUninstallMd5(uninstallMd5);
            session.setDiskSignatureMd5(diskSignatureMd5);

            sessionService.saveSession(session);

            // TODO: Get global rank and pass it to the packet below

            user.sendPacket(
                    new PacketUserPresence(
                            true,
                            user.getId(),
                            user.getUsername(),
                            Integer.parseInt(utcOffset),
                            (byte) 0, // TODO: What is this
                            geoloc.getLon(),
                            geoloc.getLat(),
                            1, // TODO: implement rank
                            4, // TODO: implement userFlags
                            Gamemode.OSU
                    )
            );

            // TODO: Fetch own stats
            user.sendPacket(new PacketNotification("Welcome to bancho.jar!"));

            // TODO: Channel
            user.sendPacket(new PacketChannelInfoEnd());

            // TODO: Fetch other sessions and send other user's presence to us

            // TODO: Check silence

            // TODO: Check restriction

            // TODO: Send friendlist packet

            // TODO: Send main menu icon packet
            return processPackets(user, session.getId().toString());
        } else {
            user = new User();
            user.sendPacket(new PacketUserID(-1));
            user.sendPacket(new PacketNotification("Incorrect username or password."));
            return processPackets(user, "no");
        }
    }

    private ResponseEntity<Resource> handleBanchoRequest(MultiValueMap<String, String> headers, String data) throws IOException {
        Session session = sessionService.getSessionByID(UUID.fromString(headers.getFirst("osu-token")));

        if (session == null) {
            User user = new User();
            user.sendPacket(new PacketRestart(0));
            user.sendPacket(new PacketNotification("The server has restarted."));
            return processPackets(user, null);
        }

        // TODO: Handle packets from body
        return null;
    }

    private ResponseEntity<Resource> processPackets(User user, String choToken) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        if (choToken != null) {
            responseHeaders.add("cho-token", choToken);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteDataOutputStream output = new ByteDataOutputStream(out, user);
        user.sendData(output);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(out.toByteArray()));
    }
}
