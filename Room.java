import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Frederick Lamptey
 * @version 2022.10.2022
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private ArrayList<Item> items;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<>();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * associates items to rooms
     * @param name of item being passed in.
     * @return arrayList of items room holds
     */
     public ArrayList getItem(){
    return items;
    }
    
    /**
     * Associates items with rooms.
     * @param name of item being passed in.
     * Removes items from a room.
     */
     public void removeItem(){
        items.clear();
    }
    
    /**
     * associates items with rooms.
     * @param name of item being passed in.
     * @return number of items a room contains.
     */
    public int numberItem(){
    return items.size();
    }
    
     /**
     * Allows items to be associated with rooms.
     * @param name of item being passed in.
     */
    public void setItem(Item name){
    if (name != null){
    items.add(name);    
    }
}

/**
*Prints weight and description of item   
*/
public void printItem(){
    {
         for(Item i: items){
            System.out.println(i.getDescription() + ". The weight of the " +
            "item is " + i.getWeight());
        }
    }
}
}

