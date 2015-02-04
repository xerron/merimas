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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;

public class Search {

	public static final int CONSONANT = 0;
	public static final int ASONANCE = 1;
	public static final int VOH = 1;
	public static final int INDIFERENT = -1;

	private SearchResult[][] acResults;

	public Search(String word, int rhymeType, int silables, int beggining,
			String basePath) {

		UserPreferences preferences = new UserPreferences(basePath);
		RhymeFinder finder = new RhymeFinder(word);

		Vector<Vector<SearchResult>> result = new Vector<Vector<SearchResult>>();
		String rhyme = finder.getRhyme();
		if (preferences.isCeEquivalence())
			rhyme = rhyme.replace("ce", "se").replace("ci", "si");
		if (preferences.isLiEquivalence())
			rhyme = rhyme.replace("ll", "li");
		if (preferences.isNiEquivalence())
			rhyme = rhyme.replace("�i", "ni");
		if (preferences.isYEquivalence())
			rhyme = rhyme.replace("y", "ll");
		if (preferences.isZEquivalence())
			rhyme = rhyme.replace("z", "s");

		for (int i = 0; i < 3; i++) {
			Vector<SearchResult> res = new Vector<SearchResult>();
			Vector<FileToSearch> files = getFiles(i, finder.getAsonance(),
					basePath);
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = files.iterator(); iterator.hasNext();) {
				FileToSearch fs = (FileToSearch) iterator.next();
				boolean hasRhyme = (rhymeType == ASONANCE || fs.fits(finder
						.getRhyme()));
				if (rhymeType != ASONANCE) {
					if (!hasRhyme && preferences.isCeEquivalence()) {
						hasRhyme = fs.fits(finder.getRhyme()
								.replace("ce", "se").replace("ci", "si"));
						if (!hasRhyme)
							hasRhyme = fs.fits(finder.getRhyme()
									.replace("se", "ce").replace("si", "ci"));
					}
					if (!hasRhyme && preferences.isLiEquivalence()) {
						hasRhyme = fs.fits(finder.getRhyme()
								.replace("ll", "li"));
						if (!hasRhyme)
							hasRhyme = fs.fits(finder.getRhyme().replace("li",
									"ll"));
					}
					if (!hasRhyme && preferences.isNiEquivalence()) {
						hasRhyme = fs.fits(finder.getRhyme()
								.replace("�i", "ni"));
						if (!hasRhyme)
							hasRhyme = fs.fits(finder.getRhyme().replace("ni",
									"�i"));
					}
					if (!hasRhyme && preferences.isYEquivalence()) {
						hasRhyme = fs
								.fits(finder.getRhyme().replace("y", "ll"));
						if (!hasRhyme)
							hasRhyme = fs.fits(finder.getRhyme().replace("ll",
									"y"));
					}
					if (!hasRhyme && preferences.isZEquivalence()) {
						hasRhyme = fs.fits(finder.getRhyme().replace("z", "s"));
						if (!hasRhyme)
							hasRhyme = fs.fits(finder.getRhyme().replace("s",
									"z"));
					}
				}
				if (fs.getFile().exists() && hasRhyme) {

					try {
						BufferedReader in = new BufferedReader(new FileReader(
								fs.getFile()));
						String line = null;
						while ((line = in.readLine()) != null) {
							String[] parts = line.split("\t");
							int silCount = Integer.parseInt(parts[4]);
							boolean filter = true;
							if (rhymeType == Search.CONSONANT) {
								String newRhyme = parts[3];
								if (preferences.isCeEquivalence())
									newRhyme = newRhyme.replace("ce", "se")
											.replace("ci", "si");
								if (preferences.isLiEquivalence())
									newRhyme = newRhyme.replace("ll", "li");
								if (preferences.isNiEquivalence())
									newRhyme = newRhyme.replace("�i", "ni");
								if (preferences.isYEquivalence())
									newRhyme = newRhyme.replace("y", "ll");
								if (preferences.isZEquivalence())
									newRhyme = newRhyme.replace("z", "s");
								if (!rhyme.equals(newRhyme))
									filter = false;
							}

							if (filter) {
								if (silables != INDIFERENT
										&& silables != silCount)
									filter = false;
								else if (beggining != INDIFERENT
										&& beggining != Integer
												.parseInt(parts[5]))
									filter = false;
							}

							if (filter) {
								res.add(new SearchResult(parts[0], parts[1],
										parts[6], parts[2], silCount));

							}

						}

						in.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			}
			result.add(res);
		}

		String[][] words = new String[3][];
		for (int i = 0; i < 3; i++)
			words[i] = new String[result.get(i).size()];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < words[i].length; j++)
				words[i][j] = result.get(i).get(j).getComparableWord(j);
		}

		for (int i = 0; i < 3; i++)
			Arrays.sort(words[i]);

		acResults = new SearchResult[3][];
		for (int i = 0; i < 3; i++)
			acResults[i] = new SearchResult[words[i].length];

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < words[i].length; j++) {
				String locS = words[i][j].substring(31, words[i][j].length());
				int loc = Integer.parseInt(locS);
				acResults[i][j] = result.get(i).get(loc);
			}
		}

	}

	public SearchResult[] getSus() {
		return acResults[1];
	}

	public SearchResult[] getVerbs() {
		return acResults[0];
	}

	public SearchResult[] getRest() {
		return acResults[2];
	}

	private Vector<FileToSearch> getFiles(int index, String asonance,
			String basePath) {
		Vector<FileToSearch> res = new Vector<FileToSearch>();
		File indexFile = new File(basePath + "DDBB/" + index + "_" + asonance
				+ "_index.txt");
		if (!indexFile.exists())
			res.add(new FileToSearch(new File(basePath + "DDBB/" + index + "_"
					+ asonance + ".txt"), "a", "zzzzzzzzzz"));
		else {
			try {
				BufferedReader in = new BufferedReader(
						new FileReader(indexFile));
				int count = 0;
				String line = null;
				while ((line = in.readLine()) != null) {
					if (line.length() > 0) {
						String[] ft = line.split("\t");
						res.add(new FileToSearch(
								new File(basePath + "DDBB/" + index + "_"
										+ asonance + "_" + count + ".txt"),
								ft[0], ft[1]));
						count++;
					}
				}

				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return res;
	}

	private class FileToSearch {
		private File file;
		private String from;
		private String to;

		public FileToSearch(File file, String from, String to) {

			this.file = file;
			this.from = from;
			this.to = to;
		}

		public File getFile() {
			return file;
		}

		public boolean fits(String rhyme) {
			int f = rhyme.compareTo(from);
			int t = rhyme.compareTo(to);

			return f >= 0 && t <= 0;
		}
	}

}
