package tp1.view;

import tp1.util.MyStringUtils;

public class Messages {
	
	public static final String VERSION = "3.0";

	public static final String GAME_NAME = "MarioBross";

	public static final String USAGE = "Usage: %s [<level>]".formatted(GAME_NAME);

	public static final String WELCOME = String.format("%s %s%n", GAME_NAME, VERSION);

	public static final String LEVEL_NOT_A_NUMBER = "The level must be a number";
	public static final String INVALID_LEVEL_NUMBER = "Not valid level number";

	public static final String LEVEL_NOT_A_NUMBER_ERROR = String.format("%s: %%s", LEVEL_NOT_A_NUMBER);

	public static final String PROMPT = "Command > ";

	public static final String DEBUG = "[DEBUG] Executing: %s%n";
	public static final String ERROR = "[ERROR] Error: %s";

	// GAME STATUS
	public static final String NUMBER_OF_CYCLES = "Number of cycles: %s";

	public static final String REMAINING_TIME = "Time: %s";
	public static final String POINTS = "Points: %s";
	public static final String NUM_LIVES = "Lives: %s";

	// GAME END MESSAGE
	public static final String GAME_OVER = "Game over";
	public static final String PLAYER_QUITS = "Player leaves the game";
	public static final String MARIO_WINS = "Thanks, Mario! Your mission is complete.";
	// Position format
	public static final String POSITION = "(%s,%s)";

	// Other
	public static final String SPACE = " ";
	public static final String TAB = "   ";
	public static final String LINE_SEPARATOR = System.lineSeparator();
	public static final String LINE = "%s" + LINE_SEPARATOR;
	public static final String LINE_TAB = TAB + LINE;
	public static final String LINE_2TABS = TAB + LINE_TAB;
	// FileGameConfiguration
	public static final String FILEGAMECONFIGURATION_NOT_FOUND = "File not found: \"%s\"";
	public static final String FILEGAMECONFIGURATION_INCORRECT_GAME_STATUS = "Incorrect game status \"%s\"";
	public static final String FILEGAMECONFIGURATION_INVALID_FILE_CONFIG = "Invalid file \"%s\" configuration";

//Commands
	// Errores de factorías:
	public static final String UNKNOWN_COMMAND = "Unknown command: %s";
	public static final String INVALID_GAME_OBJECT = "Invalid game object: %s";
	// Errores de commandos:
	public static final String COMMAND_PARAMETERS_MISSING = "Missing parameters";
	public static final String COMMAND_INCORRECT_PARAMETER_NUMBER = "Incorrect parameter number";
	public static final String UNKNOWN_ACTION = "Unknown action: \"%s\"";
	public static final String ILLEGAL_ACTION = "Illegal action: \"%s\"";
	public static final String INVALID_COMMAND = "Invalid command: %s";
	public static final String INVALID_COMMAND_PARAMETERS = "Invalid command parameters";
	public static final String ERROR_COMMAND_EXECUTE = "Command execute problem";
	
	public static final String HELP_AVAILABLE_COMMANDS = "Available commands:";
	@Deprecated
	/* @formatter:off */
	public static final String[] HELP_LINES = new String[] { HELP_AVAILABLE_COMMANDS,
		"[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+: user performs actions",
		"[u]pdate | \"\": user does not perform any action",
		"[r]eset [numLevel]: reset the game to initial configuration if not numLevel else load the numLevel map",
		"[h]elp: print this help message",
		"[e]xit: exits the game"
	};
	/* @formatter:on */
	@Deprecated
	public static final String HELP = String.join(LINE_SEPARATOR+"   ", HELP_LINES) + LINE_SEPARATOR;
	public static final String COMMAND_HELP_TEXT = "%s: %s";
	
	// UPDATE
	public static final String COMMAND_UPDATE_NAME = "update";
	public static final String COMMAND_UPDATE_SHORTCUT = "u";
	public static final String COMMAND_UPDATE_DETAILS = "[u]pdate | \"\"";
	public static final String COMMAND_UPDATE_HELP = "user does not perform any action";
		
	// EXIT
	public static final String COMMAND_EXIT_NAME = "exit";
	public static final String COMMAND_EXIT_SHORTCUT = "e";
	public static final String COMMAND_EXIT_DETAILS = "[e]xit";
	public static final String COMMAND_EXIT_HELP = "exits the game";
	
	// HELP
	public static final String COMMAND_HELP_NAME = "help";
	public static final String COMMAND_HELP_SHORTCUT = "h";
	public static final String COMMAND_HELP_DETAILS = "[h]elp";
	public static final String COMMAND_HELP_HELP = "print this help message";
	
	// RESET
	public static final String COMMAND_RESET_NAME = "reset";
	public static final String COMMAND_RESET_SHORTCUT = "r";
	public static final String COMMAND_RESET_DETAILS = "[r]eset [numLevel]";
	public static final String COMMAND_RESET_HELP = "reset the game to initial configuration if not numLevel else load the numLevel map";
	
	// ACTION
	public static final String COMMAND_ACTION_NAME = "action";
	public static final String COMMAND_ACTION_SHORTCUT = "a";
	public static final String COMMAND_ACTION_DETAILS = "[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+";
	public static final String COMMAND_ACTION_HELP = "user performs actions";
	public static final String COMMAND_ACTION_INCORRECT = "Incorrect 'action command', because the action list is empty (all actions are unknown).";
	
	// ADDOBJECTCOMMAND
	public static final String COMMAND_ADDOBJECT_NAME = "addObject";
	public static final String COMMAND_ADDOBJECT_SHORTCUT = "aO";
	public static final String COMMAND_ADDOBJECT_DETAILS = "[a]dd[O]bject <object_description>";
	public static final String COMMAND_ADDOBJECT_HELP = "adds to the board the object given by object_description.";
	public static final String COMMAND_ADDOBJECT_EXECUTION_EXAMPLE = "<object_description> = (col,row) objName [dir [BIG|SMALL]]. Ej. (12,3) Mario LEFT SMALL";
	public static final String COMMAND_ADDOBJECT_ERROR = "Object parse error, too much args: \"%s\""; //!
	public static final String COMMAND_ADDOBJECT_FORMAT_ERROR = "Incorrect format";
	
	//SAVECOMMAND
	public static final String COMMAND_SAVE_NAME = "save";
	public static final String COMMAND_SAVE_SHORTCUT = "s";
	public static final String COMMAND_SAVE_DETAILS = "[s]ave <fileName>";
	public static final String COMMAND_SAVE_HELP = "save the actual configuration in text file <fileName>";
	public static final String COMMAND_SAVE_ERROR = "Cannot save game in file";
	public static final String COMMAND_SAVE_CORRECT = TAB + "File \"%s\" correctly saved" + LINE_SEPARATOR;
	
	// LOADCOMMAND
	public static final String COMMAND_LOAD_NAME = "load";
	public static final String COMMAND_LOAD_SHORTCUT = "l";
	public static final String COMMAND_LOAD_DETAILS = "[l]oad <fileName>";
	public static final String COMMAND_LOAD_HELP = "load the game configuration from text file <fileName>";
	public static final String COMMAND_LOAD_UNKNOWN_FILE = "Unable to load game configuration from file \"%s\"";
	
	//GAMEMODEL EXCEPTIONS
	public static final String POS_OFFBOARD = "Object position is off board: \"%s\"";
	public static final String INVALID_OBJ_POS = "Invalid object position: \"%s\"";
	public static final String INVALID_POS = "Invalid position: \"%s\"";
	public static final String UNKNOWN_DIR = "Unknown moving object direction: \"%s\"";
	public static final String INVALID_DIR = "Unknown action: '%s'";
	public static final String UNKNOWN_OBJECT = "Unknown game object: \"%s\"";
	public static final String INVALID_BOX_STATUS = "Invalid Box status: \"%s\"";
	public static final String INVALID_MARIO_SIZE = "Invalid Mario size: \"%s\"";
	
	//GAMEOBJECTS
	
	//GOOMBA
	public static final String OBJECT_GOOMBA_NAME = "Goomba";
	public static final String OBJECT_GOOMBA_SHORTCUT = "G";
	//MARIO
	public static final String OBJECT_MARIO_NAME = "Mario";
	public static final String OBJECT_MARIO_SHORTCUT = "M";
	//LAND
	public static final String OBJECT_LAND_NAME = "Land";
	public static final String OBJECT_LAND_SHORTCUT = "L";
	//EXITDOOR
	public static final String OBJECT_EXITDOOR_NAME = "ExitDoor";
	public static final String OBJECT_EXITDOOR_SHORTCUT = "ED";
	//MUSHROOM
	public static final String OBJECT_MUSHROOM_NAME = "Mushroom";
	public static final String OBJECT_MUSHROOM_SHORTCUT = "MU";
	//BOX
	public static final String OBJECT_BOX_NAME = "Box";
	public static final String OBJECT_BOX_SHORTCUT = "B";
	//ACTIONS
	public static final String ACTION_LEFT = "LEFT";
	public static final String ACTION_LEFT_SHORTCUT = "L";
	public static final String ACTION_RIGHT = "RIGHT";
	public static final String ACTION_RIGHT_SHORTCUT = "R";
	public static final String ACTION_STOP = "STOP";
	public static final String ACTION_STOP_SHORTCUT = "S";
	public static final String ACTION_DOWN = "DOWN";
	public static final String ACTION_DOWN_SHORTCUT = "D";
	public static final String ACTION_UP = "UP";
	public static final String ACTION_UP_SHORTCUT = "U";
	//STATES
	public static final String STATE_BIG = "BIG";
	public static final String STATE_BIG_SHORTCUT = "B";
	public static final String STATE_SMALL = "SMALL";
	public static final String STATE_SMALL_SHORTCUT = "S";
	public static final String STATE_BOX_FULL = "FULL";
	public static final String STATE_BOX_FULL_SHORTCUT = "F";
	public static final String STATE_BOX_EMPTY = "EMPTY";
	public static final String STATE_BOX_EMPTY_SHORTCUT = "E";
	
//Symbols
	public static final String EMPTY = "";
	public static final String LAND = MyStringUtils.repeat("▓",ConsoleView.CELL_SIZE);
	public static final String EXIT_DOOR = "🚪";
	public static final String MARIO_STOP = "🧑";
	public static final String MARIO_RIGHT = "🧍";//"🧍➡️";
	public static final String MARIO_LEFT = "🚶";//"⬅️🚶";
	public static final String GOOMBA = "🐻";
	
	public static final String MUSHROOM = "🍄";
	public static final String BOX = MyStringUtils.repeat("?",ConsoleView.CELL_SIZE);
	public static final String EMPTY_BOX = MyStringUtils.repeat("0",ConsoleView.CELL_SIZE);
}