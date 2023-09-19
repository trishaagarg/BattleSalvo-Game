package cs3500.pa04.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.pa04.json.Fleets;
import cs3500.pa04.json.GameStatusJson;
import cs3500.pa04.json.JoinJson;
import cs3500.pa04.json.JsonUtils;
import cs3500.pa04.json.MessageJson;
import cs3500.pa04.json.SetupRequestJson;
import cs3500.pa04.json.VolleyJson;
import cs3500.pa04.model.Coord;
import cs3500.pa04.model.Ship;
import java.util.List;

/**
 * This class uses the Proxy Pattern to talk to the Server and dispatch methods to the Player.
 */
public class MessageHandler {
  private final ProxyPlayer proxyPlayer;
  private final Player cpuPlayer;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Creates a new MessageHandler with the specified CPU player and Proxy player.
   *
   * @param cpuPlayer the CPU player
   * @param proxyPlayer the Proxy player
   */
  public MessageHandler(Player cpuPlayer, ProxyPlayer proxyPlayer) {
    this.proxyPlayer = proxyPlayer;
    this.cpuPlayer = cpuPlayer;
  }

  private static final JsonNode VOID_RESPONSE =
      new ObjectMapper().getNodeFactory().textNode("void");

  /**
   * Handles the incoming message based on its name.
   *
   * @param name the name of the message
   * @param arguments the arguments of the message
   */
  public void handleMessage(String name, JsonNode arguments) {
    if ("join".equals(name)) {
      handleJoin();
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots();
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Handles the "end-game" message.
   *
   * @param arguments the arguments of the message
   */
  private void handleEndGame(JsonNode arguments) {
    GameStatusJson gameStatus = this.mapper.convertValue(arguments, GameStatusJson.class);
    cpuPlayer.endGame(gameStatus.toGameResult(), gameStatus.reason());
  }

  /**
   * Handles the "successful-hits" message.
   *
   * @param arguments the arguments of the message
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    VolleyJson volley = this.mapper.convertValue(arguments, VolleyJson.class);
    cpuPlayer.successfulHits(volley.toCoords());
    MessageJson messageJson = new MessageJson("successful-hits", VOID_RESPONSE);
    proxyPlayer.sendResponse(messageJson);
  }

  /**
   * Handles the "report-damage" message.
   *
   * @param arguments the arguments of the message
   */
  private void handleReportDamage(JsonNode arguments) {
    VolleyJson volley = this.mapper.convertValue(arguments, VolleyJson.class);
    List<Coord> coords = cpuPlayer.reportDamage(volley.toCoords());
    VolleyJson volleyDamaged = VolleyJson.toVolley(coords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(volleyDamaged);
    MessageJson messageJson = new MessageJson("report-damage", jsonResponse);
    proxyPlayer.sendResponse(messageJson);
  }

  /**
   * Handles the "take-shots" message.
   */
  private void handleTakeShots() {
    List<Coord> coords = cpuPlayer.takeShots();
    VolleyJson volley = VolleyJson.toVolley(coords);
    JsonNode jsonResponse = JsonUtils.serializeRecord(volley);
    MessageJson messageJson = new MessageJson("take-shots", jsonResponse);
    proxyPlayer.sendResponse(messageJson);
  }

  /**
   * Handles the "setup" message.
   *
   * @param arguments the arguments of the message
   */
  private void handleSetup(JsonNode arguments) {
    SetupRequestJson setupArgs = this.mapper.convertValue(arguments, SetupRequestJson.class);
    List<Ship> shipList = cpuPlayer.setup(setupArgs.height(), setupArgs.width(),
        setupArgs.fleetSpec().toShipFleets());
    // aiPlayer.setup(setupArgs.height(), setupArgs.width(), setupArgs.fleetSpec().toShipFleets());
    Fleets fleets = Fleets.toFleets(shipList);
    JsonNode jsonResponse = JsonUtils.serializeRecord(fleets);
    MessageJson messageJson = new MessageJson("setup", jsonResponse);
    proxyPlayer.sendResponse(messageJson);
  }

  /**
   * Handles the "join" message.
   */
  private void handleJoin() {
    JoinJson response = new JoinJson(cpuPlayer.name(), "SINGLE");
    JsonNode jsonResponse = JsonUtils.serializeRecord(response);
    MessageJson messageJson = new MessageJson("join", jsonResponse);
    proxyPlayer.sendResponse(messageJson);
  }
}

