package tp1.logic;

import java.util.Objects;

public class Position {

	private final int col;
	private final int row;
	
	//CONSTRUCTORAS
	public Position() {
		col = 0;
		row = 0;
	}
	public Position(int r, int c) {
		this.col = c;
		this.row = r;
	}
	
	/*
	//MÉTODOS
	public boolean equals(Position pos) { //compara dos posiciones no la dir de memoria
		return (pos.col == this.col && pos.row == this.row);
	}
	
	*/
	@Override
	public int hashCode() {
		return Objects.hash(col, row);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		return col == other.col && row == other.row;
	}
	
	public Position movePos(Action act) { //devuelve la posición adyacente según una dirección dada
		return new Position(this.row + act.getY(), this.col + act.getX());
	}
	
	public boolean exitsBoard() { //true = la pos está fuera del tablero
		return ((this.col >= Game.DIM_X || this.col < 0)||(this.row >= Game.DIM_Y || this.row < 0));
	}
	
	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}
	//Método llamado por el parse de GameObject.
	public static Position parsePos(String[] objDescription) { 
		//1. variable auxiliar coge el string de la posición con formato "(x,y)" de objDescription[0].
		String posInStr = objDescription[0];
		//2. verificamos si la posición dada por el usuario está entre paréntesis con .startsWith y .endsWith.
		if(posInStr.startsWith("(") && posInStr.endsWith(")")) { 
			//3.Creamos una array de coordenadas
			String[] coords = posInStr.substring(1, posInStr.length() - 1).split(","); 
			//Creamos una substring de la original ignorando los parentesis y la separamos en dos mitades a partir de la coma: coords[0] = x, coords[1] = y.
		    if(coords.length == 2) { 
		    	try {
		    		//4. Utilizamos este método de integer para obtener un número de una string.
		    		int row = Integer.parseInt(coords[0]);
		    		
		    		int col = Integer.parseInt(coords[1]);
		    		//5. Después creamos la posición con los valores obtenidos.
		    		Position pos = new Position(row, col);
		    		//6. Comprobamos que la posición introducida no está fuera del tablero.
		    		if(!(pos.exitsBoard())) {
		    			//7. devolvemos la posición para el parse del objeto.
		    			return pos; 
		    		}
		    	} catch (NumberFormatException e) {
		    		return null;
		    	}
		    }
		}
		return null;
	}
}

/* Explicación del try-catch en position.
 * Sabemos que ya existe un try-catch de NumberFormatException en main. Pero el mensaje de error que este imprime
 * es un "wrong level number", para manejar la excepción al poner un caracter o un número no reconocido
 * como argumento para resetear el nivel.
 * Para que el mensaje sea más concreto, y el error impreso sea el real, es decir, "no podemos crear un objeto en la
 * posición dada porque has metido algo que no es un número, o que no se puede parsear a un número", hemos decidido
 * añadir también este try catch específico para Position.
 * 
 */
		    
