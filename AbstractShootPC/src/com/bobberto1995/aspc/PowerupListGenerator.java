package com.bobberto1995.aspc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class PowerupListGenerator
{
	public static ArrayList<Powerup> powerupList = new ArrayList<Powerup>();
	
	public static ArrayList<Powerup> getListFromFile(String filename) throws FileNotFoundException
	{
		ArrayList<Powerup> result = new ArrayList<Powerup>();
		
		BufferedReader infile = new BufferedReader(new FileReader(filename));
		
		Scanner powerupReader = new Scanner(infile);
		powerupReader.useDelimiter("PWUP");
		while(powerupReader.hasNext())
		{
			Scanner powerupParser = new Scanner(powerupReader.next().trim());
			
			int theType = -1;
			String theParm = "";
			
			while(powerupParser.hasNextLine())
			{
				String line = powerupParser.nextLine().trim();
				if(line.startsWith("TYPE"))
				{
					theType = Integer.parseInt(line.substring(5));
				}
				else if(line.startsWith("PARM"))
				{
					theParm = line.substring(5);
				}
			}
			if(theType >= 0)
			{
				Powerup thePowerup = new Powerup(null,0,0,theType,theParm);
				result.add(thePowerup);
			}
			powerupParser.close();
		}
		powerupReader.close();
		return result;
	}
	
	public static void generateListFromFile(String filename) throws FileNotFoundException
	{
		PowerupListGenerator.powerupList = PowerupListGenerator.getListFromFile(filename);
	}
	
	public static Powerup getRandomPowerup()
	{
		return powerupList.get((int)(Math.random() * powerupList.size()));
	}
}