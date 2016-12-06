import java.awt.Color;
import java.util.Random;


public class Organism {
	String name;
	int lifeTime;
	int adultLife;
	char identifier;
	int mutation_prob;
	int reproduction;
	int spread;
	int death_conditions;
	Color org_color;
	int population;
	public Organism(String name,char identifier, int mutation_prob, int reproduction, int spread,
			int death_conditions, Color org_color) {
		super();
		this.identifier = identifier;
		this.mutation_prob = mutation_prob;
		this.reproduction = reproduction;
		this.spread = spread;
		this.death_conditions = death_conditions;
		this.org_color = org_color;
		this.name=name;
		
	}
	@Override
	public String toString() {
		return "Organism [name=" + name + ", identifier=" + identifier
				+ ", mutation_prob=" + mutation_prob + ", reproduction="
				+ reproduction + ", spread=" + spread + ", death_conditions="
				+ death_conditions + ", org_color=" + org_color + "]";
	}
	public char getIdentifier() {
		return identifier;
	}
	public void setIdentifier(char identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getReproduction() {
		return reproduction;
	}
	public void setReproduction(int reproduction) {
		this.reproduction = reproduction;
	}
	public int getMutation_prob() {
		return mutation_prob;
	}
	public void setMutation_prob(int mutation_prob) {
		this.mutation_prob = mutation_prob;
	}
	public int getReprodution() {
		return reproduction;
	}
	public void setReprodution(int reproduction) {
		this.reproduction = reproduction;
	}
	public int getSpread() {
		return spread;
	}
	public void setSpread(int spread) {
		this.spread = spread;
	}
	public int getDeath_conditions() {
		return death_conditions;
	}
	public void setDeath_conditions(int death_conditions) {
		this.death_conditions = death_conditions;
	}
	public Color getOrg_color() {
		return org_color;
	}
	public void setOrg_color(Color org_color) {
		this.org_color = org_color;
	}
	public Organism Mutate()
	{
		Random rand = new Random(); 
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color c =new Color(r, g, b);
		return new Organism(this.name+this.reproduction+""+this.spread+this.death_conditions,(char)(rand.nextInt(26) + 'a'),this.mutation_prob,1+rand.nextInt(3), 1+rand.nextInt(10),rand.nextInt(3),c);
	}
	public void selfMutate(){
		Random rand = new Random(); 
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		Color c =new Color(r, g, b);
		//this.mutation_prob = rand.nextInt(2)+rand.nextInt(mutation_prob);
		this.reproduction = 1+rand.nextInt(2)+rand.nextInt(8);
		this.spread = 1+rand.nextInt(2)+rand.nextInt(this.spread);
		this.death_conditions =1+ rand.nextInt(2)+rand.nextInt(death_conditions);
		this.org_color = c;
	}
	
}
