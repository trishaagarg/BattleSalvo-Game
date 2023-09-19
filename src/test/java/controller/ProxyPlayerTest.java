package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import cs3500.pa04.controller.CpuPlayer;
import cs3500.pa04.controller.ProxyPlayer;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestJson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ProxyPlayer class.
 */
public class ProxyPlayerTest {

  private ByteArrayOutputStream testLog;
  private ProxyPlayer player;

  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  @Test
  public void testPlayerRun() {
    FleetSpecJson fleetSpecJson = new FleetSpecJson(1, 1, 1, 2);
    SetupRequestJson setupRequestJson = new SetupRequestJson(6, 6, fleetSpecJson);
    JsonNode jsonNode = createSampleMessage("setup", setupRequestJson);
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.player = new ProxyPlayer(socket, new CpuPlayer());
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    this.player.run();
  }

  @Test
  public void testPlayerSendResponseToServer() {
    FleetSpecJson fleetSpecJson = new FleetSpecJson(1, 1, 1, 2);
    SetupRequestJson setupRequestJson = new SetupRequestJson(6, 6, fleetSpecJson);
    JsonNode jsonNode = createSampleMessage("setup", setupRequestJson);
    Mocket socket = new Mocket(this.testLog, List.of(jsonNode.toString()));

    try {
      this.player = new ProxyPlayer(socket, new CpuPlayer());
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }
    MessageJson messageJson = new MessageJson("join",
        JsonUtils.serializeRecord(setupRequestJson));
    this.player.sendResponse(messageJson);
  }

  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }

  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
