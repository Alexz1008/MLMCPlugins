package me.neoblade298.neouno.Commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.neoblade298.neouno.Uno;
import me.neoblade298.neouno.Objects.Game;
import me.neoblade298.neouno.Objects.GamePlayer;
import me.neoblade298.neouno.Objects.Lobby;

public class LobbyCommands {
	Uno main;

	public LobbyCommands(Uno main) {
		this.main = main;
	}

	public void createLobby(String name, Player sender) {

		UUID uuid = sender.getUniqueId();
		// Check if the name exists already, or player is already in a game
		if (main.inlobby.containsKey(uuid)) {
			sender.sendMessage("�4[�c�lMLMC�4] �cYou're already in a lobby!");
			return;
		}
		else if (main.ingame.containsKey(uuid)) {
			sender.sendMessage("�4[�c�lMLMC�4] �cYou're already in a game!");
			return;
		}
		else if (main.lobbies.containsKey(name) || main.games.containsKey(name)) {
			sender.sendMessage("�4[�c�lMLMC�4] �cThat game name is taken!");
			return;
		}

		Lobby lobby = new Lobby(uuid, name);
		main.inlobby.put(uuid, lobby);
		main.lobbies.put(name, lobby);
		sender.sendMessage("�4[�c�lMLMC�4] �7Successfully created lobby �e" + lobby.getName() + "�7!");
	}

	public void joinLobby(String name, Player sender) {
		if (!main.lobbies.containsKey(name)) {
			sender.sendMessage("�4[�c�lMLMC�4] �cThat lobby doesn't exist!");
			return;
		}

		UUID uuid = sender.getUniqueId();
		Lobby lobby = main.lobbies.get(name);
		ArrayList<UUID> invited = lobby.getInvited();
		if (invited.contains(uuid)) {
			if (lobby.getPlayers().size() <= 7) {
				sender.sendMessage("�4[�c�lMLMC�4] �7Successfully joined lobby �e" + lobby.getName() + "�7!");
				lobby.broadcast("&e" + sender.getName() + " &7has joined the lobby!");
				lobby.getPlayers().add(uuid);
				lobby.getInvited().remove(uuid);
				main.inlobby.put(uuid, lobby);
			}
			else {
				sender.sendMessage("�4[�c�lMLMC�4] �cThat lobby is full!");
			}
		}
		else {
			sender.sendMessage("�4[�c�lMLMC�4] �cYou aren't invited to that lobby!");
		}
	}

	public void leaveLobby(Player sender) {
		UUID suuid = sender.getUniqueId();
		Lobby lobby = main.inlobby.get(suuid);
		if (lobby.getHost().equals(suuid)) {
			lobby.broadcast("&7Lobby disbanded by host!");
			for (UUID uuid : lobby.getPlayers()) {
				main.inlobby.remove(uuid);
			}
			main.lobbies.remove(lobby.getName());
		}
		else {
			lobby.getPlayers().remove(sender);
			main.inlobby.remove(sender);
			sender.sendMessage("�4[�c�lMLMC�4] �7Successfully left lobby!");
			lobby.broadcast("&e" + sender.getName() + " &7has left the lobby!");
		}
	}

	public void kickPlayer(Player sender, String name) {
		Lobby lobby = main.inlobby.get(sender);
		Player toKick = Bukkit.getPlayer(name);
		if (toKick == null) {
			return;
		}
		if (lobby.getHost().equals(sender)) {
			lobby.getPlayers().remove(Bukkit.getPlayer(name));
			Bukkit.getPlayer(name).sendMessage("�7You were kicked from the lobby!");
			lobby.broadcast("&e" + Bukkit.getPlayer(name).getName() + "&7 has been kicked by the host!");
		}
		else {
			sender.sendMessage("�4[�c�lMLMC�4] �cOnly hosts can kick from lobby!");
		}
	}

	public void invitePlayer(Player sender, String name) {
		Lobby lobby = main.inlobby.get(sender);
		Player invited = Bukkit.getPlayer(name);
		UUID uuid = invited.getUniqueId();
		if (invited == null) {
			return;
		}
		if (lobby.getHost().equals(sender)) {
			lobby.getInvited().add(uuid);
			lobby.broadcast("&7Successfully invited &e" + invited.getName() + "&7!");
			invited.sendMessage("�4[�c�lMLMC�4] �7You were invited to uno lobby �e" + lobby.getName() + "�7! Join with �c/uno join " + lobby.getName() + "�7.");
		}
		else {
			sender.sendMessage("�4[�c�lMLMC�4] �cOnly hosts can invite to lobby!");
		}
	}

	public void setPointsToWin(Player sender, String amt) {
		Lobby lobby = main.inlobby.get(sender);
		int amount = 0;
		try {
			amount = Integer.parseInt(amt);
		} catch (NumberFormatException e) {
			sender.sendMessage("�4[�c�lMLMC�4] �cInvalid number format!");
			return;
		}
		
		if (amount < 100 || amount > 100000) {
			sender.sendMessage("�4[�c�lMLMC�4] �cAmount must be between 100 and 100000!");
			return;
		}

		if (lobby.getHost().equals(sender)) {
			lobby.setPointsToWin(amount);
			lobby.broadcast("&7Successfully set points to win to &e" + amt + "!");
		}
		else {
			sender.sendMessage("�4[�c�lMLMC�4] �cOnly hosts can change starting money!");
		}
	}

	public void startGame(Player sender) {
		Lobby lobby = main.inlobby.get(sender);
		if (lobby.getPlayers().size() >= 2) {
			if (lobby.getHost().equals(sender)) {
				try {
					Game game = new Game(main, lobby.getName(), lobby.getPlayers(), lobby.getPointsToWin());
					main.games.put(lobby.getName(), game);
					for (UUID uuid : lobby.getPlayers()) {
						main.inlobby.remove(uuid);
						main.ingame.put(uuid, game);
					}
					main.lobbies.remove(lobby.getName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else {
				sender.sendMessage("�4[�c�lMLMC�4] �cOnly hosts can start the game!");
			}
		}
		else {
			sender.sendMessage("�4[�c�lMLMC�4] �cThere must be at least 2 players to start!");
		}
	}
	
	public void spectateGame(Player sender, String name) {
		if (!main.ingame.containsKey(sender)) {
			if (main.games.containsKey(name)) {
				UUID uuid = sender.getUniqueId();
				Game game = main.games.get(name);
				GamePlayer gp = new GamePlayer(game, uuid);
				main.ingame.put(uuid, game);
				game.spectators.add(gp);
				gp.message("&7You're now spectating! Leave any time with &c/uno quit&7.");
				game.broadcast("&e" + gp + " &7is now spectating!");
			}
			else {
				sender.sendMessage("�4[�c�lMLMC�4] �cThat game doesn't exist");
			}
		}
		else {
			sender.sendMessage("�4[�c�lMLMC�4] �cYou're already in a game!");
		}
	}
}
