/***************************************************************************
 *   Copyright (C) 2009 by Eduardo Rodriguez Lorenzo                       *
 *   edu.tabula@gmail.com                                                  *
 *   http://www.cronopista.com                                             *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.             *
 ***************************************************************************/
package com.diccionarioderimas.cli;

public class SearchResult {

	private String word;

	private String originalWord;

	private String type;

	private String normalized;

	private int silables;

	private static String[] padding = new String[30];

	static {
		String t = "";
		for (int i = 0; i < 30; i++) {
			padding[i] = t;
			t += " ";
		}

	}

	public SearchResult(String word, String originalWord, String normalized,
			String type, int silables) {
		this.word = word;
		this.originalWord = originalWord;
		this.type = type;
		this.silables = silables;
		this.normalized = normalized;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getOriginalWord() {
		return originalWord;
	}

	public void setOriginalWord(String originalWord) {
		this.originalWord = originalWord;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSilables() {
		return silables;
	}

	public void setSilables(int silables) {
		this.silables = silables;
	}

	public String getTypeString() {
		char[] def = type.toCharArray();
		StringBuffer res = new StringBuffer();
		if (def[0] == 'V') {
			if (def[1] == 'I' || def[1] == 'S') {

				switch (def[3]) {
				case '1':
					res.append("primera persona singular ");
					break;
				case '2':
					res.append("segunda persona singular ");
					break;
				case '3':
					res.append("tercera persona singular ");
					break;
				case '4':
					res.append("primera persona plural ");
					break;
				case '5':
					res.append("segunda persona plural ");
					break;
				case '6':
					res.append("tercera persona plural ");
					break;
				default:
					break;
				}
				switch (def[2]) {
				case 'P':
					res.append("del presente de ");
					break;
				case 'I':
					res.append("del imperfecto de ");
					break;
				case 'F':
					res.append("del futuro de ");
					break;
				case 'C':
					res.append("del condicional de ");
					break;
				case 'X':
					res.append("del perfecto de ");
					break;

				default:
					break;
				}
				switch (def[1]) {
				case 'I':
					res.append("indicativo ");
					break;
				case 'S':
					res.append("subjuntivo ");
					break;
				default:
					break;
				}

			} else {
				switch (def[1]) {
				case 'M':
					res.append("imperativo ");
					break;
				case 'P':
					res.append("participio ");
					break;
				case 'G':
					res.append("gerundio ");
					break;
				default:
					break;
				}
				for (int i = 2; i < def.length; i++) {
					switch (def[i]) {
					case 'F':
						res.append("femenino ");
						break;
					case 'P':
						res.append("plural ");
						break;
					case 'E':
						res.append("(forma enclítica) ");
						break;

					default:
						break;
					}
				}

			}

		} else {
			for (int i = 0; i < def.length; i++) {
				switch (def[i]) {
				case 'F':
					res.append("femenino ");
					break;
				case 'P':
					res.append("plural ");
					break;
				default:
					break;
				}
			}
		}

		return res.toString();
	}

	public String getComparableWord(int loc) {

		return new StringBuffer(normalized).append(padding[30 - word.length()])
				.append('|').append(loc).toString();
	}

}
