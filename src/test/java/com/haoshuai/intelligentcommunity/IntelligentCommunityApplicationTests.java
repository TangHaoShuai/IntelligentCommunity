package com.haoshuai.intelligentcommunity;

import com.haoshuai.intelligentcommunity.chat.WebSocketChatServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.websocket.Session;
import java.io.IOException;

@SpringBootTest
class IntelligentCommunityApplicationTests {

    @Autowired
    WebSocketChatServer webSocketChatServer;
    @Test
    void contextLoads() {

    }

}
