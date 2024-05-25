package Civilisation;

public abstract class Building {
  private int cost;
  private int buildTime;

  public Building(int cost, int buildTime) {
    this.cost = cost;
    this.buildTime = buildTime;
  }

  public int getCost() {
    return cost;
  }

  public int getBuildTime(){
    return buildTime;
  }

}