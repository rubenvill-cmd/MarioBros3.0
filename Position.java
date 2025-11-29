package tp1.logic;

import java.util.Objects;
import tp1.view.Messages;

import tp1.exceptions.OffBoardException;
import tp1.exceptions.PositionParseException;

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
	public static Position parsePos(String[] objDescription) throws PositionParseException, OffBoardException { 
		String posInStr = objDescription[0]; //substring (2,1)
		
		if(posInStr.startsWith("(") && posInStr.endsWith(")")) { 
			String[] coords = posInStr.substring(1, posInStr.length() - 1).split(",");
			
			if(coords.length == 2) { 
		    	try {
		    		int row = Integer.parseInt(coords[0]);
		    		
		    		int col = Integer.parseInt(coords[1]);
		    		Position pos = new Position(row, col);
		    		if(!(pos.exitsBoard())) {
		    			return pos; 
		    		}
		    		else throw new OffBoardException(Messages.POS_OFFBOARD.formatted(String.join(" ", objDescription))); //POSICIÓN FUERA DEL RANGO DEL TABLERO (!IMPRIMIMOS TODA LA FRASE)
		    	}
		    	catch (NumberFormatException e) {
		    		throw new PositionParseException(Messages.INVALID_POS.formatted(posInStr), e); //NO CONTIENE NUMEROS; NO SE COMO PONER PARA Q TAMBIEN SE MUESTRE FOR INPUT STRING:A
		    	}
		    }
			else throw new PositionParseException(Messages.INVALID_POS.formatted(posInStr)); //FORMATO INVALIDO DE CARACTERES INSUFICIENTES
		}
		
		else throw new PositionParseException(Messages.INVALID_POS.formatted(posInStr)); //FORMATO INVALIDO DE PARENTESIS
		//return null;
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
		    
