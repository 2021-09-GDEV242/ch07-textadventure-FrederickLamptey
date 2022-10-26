import java.util.ArrayList;
import java.util.Random;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Frederick Lamptey
 * @version 2022.10.25
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Random randomizer;
    private int health;
    private ArrayList<Room> rooms;
    private ArrayList<Item> inventory;
    private Room locker;
    
    /**
     * Main method to allow game run outside of Bluej.
    */
   public static void main(String[] args){
        Game game = new Game();
        game.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        health = 0;
        inventory = new ArrayList<>();
    }

    /**
     * Create all the rooms and link their exits together.
     * Creates items and links them to rooms
     */
    private void createRooms()
    {
        rooms = new ArrayList<>();
        Room outside, theater, pub, lab, office,lobby,parking_lot,gym,locker_room,auditorium,kitchen,closet_janitor,water_closet, cafeteria;
        
        Item mango, pineapple, orange;
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        rooms.add(outside);
        theater = new Room("in a lecture theater");
        rooms.add(theater);
        pub = new Room("in the campus pub");
        rooms.add(pub);
        lab = new Room("in a computing lab");
        rooms.add(lab);
        office = new Room("in the computing admin office");
        rooms.add(office);
        lobby = new Room("in the lobby");
        rooms.add(lobby);
        parking_lot = new Room("in the parking lot");
        rooms.add(parking_lot);
        gym = new Room("in the gym");
        rooms.add(gym);
        locker_room = new Room("in the locker_room");
        rooms.add(locker_room);
        auditorium = new Room("in the auditorium");
        rooms.add(auditorium);
        kitchen = new Room("in the kitchen");
        rooms.add(kitchen);
        closet_janitor = new Room("in the closet_janitor");
        rooms.add(closet_janitor);
        water_closet = new Room("in the water_closet");
        rooms.add(water_closet);
        cafeteria = new Room("in the cafeteria");
        rooms.add(cafeteria);
        
        mango = new Item("This is a mango. This improves health when eaten", 1);
        pineapple = new Item("This is a pineapple. This improves health when eaten", 1);
        orange = new Item("This is an orange. This improves health when eaten", 1);
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", gym);
        
        gym.setExit("south", outside);
        theater.setExit("west", outside);

        pub.setExit("east", outside);
        
        lab.setExit("south", lobby);
        lab.setExit("north", outside);
        lab.setExit("west", cafeteria);
        lab.setExit("east", office);
        
        lobby.setExit("north", lab);
        lobby.setExit("west", water_closet);
        lobby.setExit("south", gym);
        lobby.setExit("east", auditorium);
        
        office.setExit("west", lab);
        
        cafeteria.setExit("west", kitchen);
        cafeteria.setExit("south", water_closet);
        cafeteria.setExit("east", lab);
        cafeteria.setItem(orange);
        cafeteria.setItem(mango);
        
        kitchen.setExit("east", cafeteria);
        kitchen.setExit("south", closet_janitor);
        
        closet_janitor.setExit("north", kitchen);
        closet_janitor.setExit("east", water_closet);
        closet_janitor.setItem(mango);
        
        water_closet.setExit("west", closet_janitor);
        water_closet.setExit("north", cafeteria);
        water_closet.setExit("east", lobby);
        
        gym.setExit("north", lobby);
        gym.setExit("east", locker_room);
        //lab.setExit("north", outside);
        //lab.setExit("east", office);
        
        auditorium.setExit("west", lobby);
        
        locker_room.setExit("west", gym);
        locker_room.setExit("east", locker);
        //office.setExit("west", lab);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
                
            case GET:
                get(command);
                break;
                
            case LOOK:
                look(command);
                break;
                
            case TRANSPORT:
                transport(command);
                break;
                
            case EAT:
                eat(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            /*
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());*/
            moveRoom(nextRoom);
        }
    }
    
    
    /** 
     * move user from current room to next room.
     * @param nextRoom room user will go next
     */
     private void moveRoom(Room nextRoom)
    {
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());
        currentRoom.printItem();
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /** 
     * "Look" was entered.Method prints exits of the room for the player.
     */
    private void look(Command command) 
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /** 
     * "Transport" was entered.Method sends user to a random room.
     */
    private void transport(Command command) 
    {
        Room room;
        if(currentRoom == locker){
            System.out.println("You cannot transport out of a locker.");
            return;
        }
        randomizer = new Random();
        while(true){
            room = rooms.get(randomizer.nextInt(rooms.size()));
            if (room != currentRoom){
                moveRoom(room);
                return;
            }
        }
    }
    
    /** 
     * "GET" was entered. Method gets the item in the room.
     */
     private void get(Command command) 
    {
        if(currentRoom.numberItem() == 0){
            System.out.println("There are no items in this room.");
        }
        else{
            inventory.addAll(currentRoom.getItem());
            
            currentRoom.removeItem();
            System.out.println("You have gotten the items in this room.");
        }
    }
    
    /** 
     * "Eat" was entered. Command allows the player to eat food and 
     * satisfy his hunger. It increases health by 10 each time a food is
     * eaten and removes the food from inventory.
     */
     private void eat(Command command) 
    {
        if (inventory.size() == 0){
            System.out.println("You do not have any food.");
        }
        else{ 
            System.out.println("Your health was " + health);
            health += 10;
            System.out.println("Your health is now " + health);
            inventory.remove(0);
        }
    }
}
