package com.bobberto1995.aspc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class WeaponListGenerator
{
	public static HashMap<String,Weapon> generateListFromFile(String filename) throws FileNotFoundException
	{
		HashMap<String,Weapon> result = new HashMap<String,Weapon>();
		
		BufferedReader infile = new BufferedReader(new FileReader(filename));
		
		Scanner weaponReader = new Scanner(infile);
		weaponReader.useDelimiter("WEAP");
		while(weaponReader.hasNext())
		{
			Scanner weaponParser = new Scanner(weaponReader.next().trim());
			String wName = "";
			boolean isProjectile = false;
			
			float theSize = 0, theSpeed = 0, theRange = 0;
			int theRate = 0, theDamage = 0;
			
			while(weaponParser.hasNextLine())
			{
				String line = weaponParser.nextLine().trim();
				
				if(line.startsWith("NAME"))
				{
					wName = line.substring(5);
				}
				else if(line.startsWith("TYPE"))
				{
					isProjectile = line.substring(5).equals("1");
				}
				else if(line.startsWith("SIZE"))
				{
					theSize = Integer.parseInt(line.substring(5)) / 50f;
				}
				else if(line.startsWith("RATE"))
				{
					theRate = Integer.parseInt(line.substring(5)) * 60;
				}
				else if(line.startsWith("SPED"))
				{
					theSpeed = Integer.parseInt(line.substring(5)) / 50f;
				}
				else if(line.startsWith("DAMG"))
				{
					theDamage = Integer.parseInt(line.substring(5));
				}
				else if(line.startsWith("RANG"))
				{
					theRange = Integer.parseInt(line.substring(5)) / 50f;
				}
			}
			if(isProjectile)
			{
				// PlayingField parent, int maxShotTime, int numFired, float spread, float size, float speed, int damage,float range
				Weapon theWeap = new Weapon(null, theRate, 1, 0, theSize, theSpeed, theDamage, theRange);
				result.put(wName, theWeap);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		try {
			HashMap<String, Weapon> foo = generateListFromFile("gamedata/weapons.ini");
			for(String key : foo.keySet())
			{
				System.out.println(key);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
