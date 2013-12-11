package com.set.game;

public class Card {

	// 0=green, 1=red, 2=purple
	public int color;
	// 0=diamond, 1=oval, 2=squiggle
	public int shape;
	// 0=empty, 1=shaded, 2=filled
	public int shading;
	// 0=1,... 
	public int number;
	
	public Card(int in_color, int in_number, int in_shape, int in_shading){
		color = in_color;
		shape = in_shape;
		shading = in_shading;
		number = in_number;
		
	}
	
	public String toString(){
		String result = numbers[number]+" "+shades[shading]+" "+colors[color]+" "+shapes[shape];
		if (number > 0) {
			result = result.concat("s");
		}
		return result;
	}

	public final String[] colors = {"Green","Red","Purple"};
	public final String[] shapes = {"Diamond","Oval","Squiggle"};
	public final String[] shades = {"Empty","Shaded","Solid"};
	public final String[] numbers = {"One","Two","Three"};
	
}
