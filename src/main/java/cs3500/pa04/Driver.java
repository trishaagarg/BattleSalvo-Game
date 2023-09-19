package cs3500.pa04;

import cs3500.pa04.controller.CpuPlayer;
import cs3500.pa04.controller.Game;
import cs3500.pa04.controller.ProxyPlayer;
import cs3500.pa04.model.GameResult;
import cs3500.pa04.play.UserInput;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * The main class that serves as the entry point for the BattleShip game.
 */
public class Driver {
  /**
   * The main method that starts the BattleShip game.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      runUserClient();
    } else if (args.length == 2) {
      runCpuClient(args[0], args[1]);
    } else {
      System.out.println("Expected two arguments: `[host] [port]`, or no arguments");
    }
  }

  /**
   * Runs the BattleShip game in user-client mode.
   * Prompts the user for input and starts the game.
   *
   * @throws IllegalStateException if there is an error in the game initialization
   */
  private static void runUserClient() throws IllegalStateException {
    GameResult gameResult = new GameResult();
    Scanner in = new Scanner(System.in);
    UserInput userInput = new UserInput(in);
    Game battleShip = new Game(gameResult, userInput);
    battleShip.beginGame(100);
  }

  /**
   * Runs the BattleShip game in CPU-client mode.
   * Connects to the server specified by the host and port, and starts the game using a CPU player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IllegalStateException if there is an error in connecting to the server or
   *                               in game initialization
   */
  private static void runCpuClient(String host, String port) throws IllegalStateException {
    try {
      Socket server = new Socket(host, Integer.parseInt(port));
      ProxyPlayer proxyPlayer = new ProxyPlayer(server, new CpuPlayer());
      proxyPlayer.run();
    } catch (IOException | IllegalStateException e) {
      System.out.println("Unable to connect to the server.");
    } catch (NumberFormatException e) {
      System.out.println("Second argument should be an integer. Format: `[host] [port]`.");
    }
  }
}
