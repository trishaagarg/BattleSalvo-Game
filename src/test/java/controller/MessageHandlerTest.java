package controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.controller.CpuPlayer;
import cs3500.pa04.controller.MessageHandler;
import cs3500.pa04.controller.Player;
import cs3500.pa04.controller.ProxyPlayer;
import cs3500.pa04.json.CoordJson;
import cs3500.pa04.json.FleetSpecJson;
import cs3500.pa04.json.GameStatusJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestJson;
import cs3500.pa04.json.VolleyJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.GameResult;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the MessageHandler class.
 */
public class MessageHandlerTest {
  private MessageHandler messageHandler;

  @Test
  public void testMessageHandlerHandlesJoinMessage() {
    Player player = mock(CpuPlayer.class);
    ProxyPlayer proxyPlayer = mock(ProxyPlayer.class);
    JoinJson joinJson = new JoinJson("test1", "SINGLE");
    MessageJson messageJson = new MessageJson("join",
        JsonUtils.serializeRecord(joinJson));
    messageHandler = new MessageHandler(player, proxyPlayer);
    JsonNode jsonNode = createSampleMessage("join", messageJson);
    messageHandler.handleMessage("join", jsonNode);
    verify(proxyPlayer, times(1)).sendResponse(any(MessageJson.class));
  }

  @Test
  public void testMessageHandlerHandlesSetupMessage() {
    Player player = mock(CpuPlayer.class);
    ProxyPlayer proxyPlayer = mock(ProxyPlayer.class);
    FleetSpecJson fleetSpecJson = new FleetSpecJson(1, 1, 1, 2);
    SetupRequestJson setupRequestJson = new SetupRequestJson(6, 6, fleetSpecJson);
    messageHandler = new MessageHandler(player, proxyPlayer);
    JsonNode jsonNode = JsonUtils.serializeRecord(setupRequestJson);
    messageHandler.handleMessage("setup", jsonNode);
    verify(proxyPlayer, times(1)).sendResponse(any(MessageJson.class));
  }

  @Test
  public void testMessageHandlerHandlesTakeShotMessage() {
    Player player = mock(CpuPlayer.class);
    ProxyPlayer proxyPlayer = mock(ProxyPlayer.class);
    List<Coord> coordList = new ArrayList<>();
    coordList.add(new Coord(1, 1));
    coordList.add(new Coord(2, 2));
    when(player.takeShots()).thenReturn(coordList);
    messageHandler = new MessageHandler(player, proxyPlayer);
    JsonNode voidNode = new ObjectMapper().getNodeFactory().textNode("void");
    messageHandler.handleMessage("take-shots", voidNode);
    verify(proxyPlayer, times(1)).sendResponse(any(MessageJson.class));
  }

  @Test
  public void testMessageHandlerHandlesReportDamageMessage() {
    Player player = mock(CpuPlayer.class);
    ProxyPlayer proxyPlayer = mock(ProxyPlayer.class);
    messageHandler = new MessageHandler(player, proxyPlayer);
    List<CoordJson> coordJsons = new ArrayList<>();
    coordJsons.add(new CoordJson(1, 1));
    coordJsons.add(new CoordJson(2, 2));
    VolleyJson volleyJson = new VolleyJson(coordJsons);
    JsonNode jsonNode = JsonUtils.serializeRecord(volleyJson);
    messageHandler.handleMessage("report-damage", jsonNode);
    verify(proxyPlayer, times(1)).sendResponse(any(MessageJson.class));
  }

  @Test
  public void testMessageHandlerHandlesSuccessfulHitsMessage() {
    Player player = mock(CpuPlayer.class);
    ProxyPlayer proxyPlayer = mock(ProxyPlayer.class);
    messageHandler = new MessageHandler(player, proxyPlayer);
    List<CoordJson> coordJsons = new ArrayList<>();
    coordJsons.add(new CoordJson(1, 1));
    coordJsons.add(new CoordJson(2, 2));
    VolleyJson volleyJson = new VolleyJson(coordJsons);
    JsonNode jsonNode = JsonUtils.serializeRecord(volleyJson);
    messageHandler.handleMessage("successful-hits", jsonNode);
    verify(proxyPlayer, times(1)).sendResponse(any(MessageJson.class));
  }

  @Test
  public void testMessageHandlerHandlesGameStatusMessage() {
    Player player = mock(CpuPlayer.class);
    ProxyPlayer proxyPlayer = mock(ProxyPlayer.class);
    messageHandler = new MessageHandler(player, proxyPlayer);
    GameStatusJson gameStatusJson = new GameStatusJson("WON", "You won");
    JsonNode jsonNode = JsonUtils.serializeRecord(gameStatusJson);
    messageHandler.handleMessage("end-game", jsonNode);
    verify(player, times(1)).endGame(any(GameResult.class),
        eq("You won"));
  }

  private JsonNode createSampleMessage(String messageName, Record messageObject) {
    MessageJson messageJson = new MessageJson(messageName,
        JsonUtils.serializeRecord(messageObject));
    return JsonUtils.serializeRecord(messageJson);
  }
}
