package es.urjccode.mastercloudapps.adcs.draughts.models;

class Turn {

    private Color color;

    Turn(){
        this.color = Color.WHITE;
    }

    void change(){
        this.color = Color.values()[(this.color.ordinal()+1)%2];
    }

    boolean isColor(Color color2) {
		  return false;
    }
    
    Color getColor() {
		  return this.color;
    }

    Color getOppositeColor() {
      return this.color.equals(Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    @Override
    public String toString(){
        return this.color.name();
    }

}