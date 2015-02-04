/**
 * @author Edwin Manuel Cerrón Angeles
 */
package com.diccionarioderimas.cli;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	static final String VERSION = "1.0";
	static String appPath = System.getProperty("user.dir") + File.separator;
	public static final int CONSONANT = 0;
	public static final int ASONANCE = 1;
	public static final int VOH = 1;
	public static final int INDIFERENT = -1;

	/**
	 * -t c-consonante a-asonante
	 * 
	 * sustantivos y adjetivos Verbos Participios y formas enclíticas
	 * 
	 * números de sílabas [indeferente, o un numero] comenzando por
	 * [indiferente, consonante, vocal o h]
	 * 
	 * Opciones fonéticas y = ll z = s ce/ci = se/si ñi = ni li = ll
	 * 
	 * example: merimas.jar -t a -n 4 -s c -w word
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		/**
		 * Defaults
		 */
		int type = CONSONANT;
		int silables = INDIFERENT;
		int beggining = INDIFERENT;
		/**
		 * CLI Options
		 */
		Options options = new Options();

		options.addOption("h", "help", false, "Muestra esta pantalla.");
		options.addOption("v", "version", false,
				"Muestra información sobre la versión.");
		options.addOption("t", "type", true,
				"Selecione el tipo: consonante(c) o asonante(a).");
		options.addOption("n", "number", true, "Numero de sílabas.");
		options.addOption("s", "start", true,
				"Comenzar con consonate(c) o vocal(v).");
		options.addOption("w", "word", true, "Palabra a buscar rima.");
		// options.addOption(Option.builder("w")
		// .required(true)
		// .longOpt("word")
		// .hasArg()
		// .desc("Palabra ha buscar la rima.")
		// .build());

		/**
		 * CLI Parser
		 */
		CommandLineParser parser = new DefaultParser();

		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);

			if (line.hasOption("help")) {
				// automatically generate the help statement
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("merimas", options);
			} else if (line.hasOption("version")) {
				System.out.println(VERSION);
			} else if (line.hasOption("w")) {
				if (line.hasOption("t")) {
					switch (line.getOptionValue("t")) {
					case "consonante":
						type = CONSONANT;
						break;
					case "c":
						type = CONSONANT;
						break;
					case "asonante":
						type = ASONANCE;
						break;
					case "a":
						type = ASONANCE;
						break;
					default:
						System.err
								.println("El valor del algumento -t debe ser: consonate(c) o asonante(a).");
						System.exit(0);
						break;
					}
				}
				if (line.hasOption("n")) {
					silables = Integer.parseInt(line.getOptionValue("n"));
				}
				if (line.hasOption("s")) {
					switch (line.getOptionValue("s")) {
					case "consonante":
						beggining = CONSONANT;
						break;
					case "c":
						beggining = CONSONANT;
						break;
					case "vocal":
						beggining = ASONANCE;
						break;
					case "v":
						beggining = ASONANCE;
						break;
					default:
						System.err
								.println("El valor del algumento -s debe ser: consonate(c) o vocal(v).");
						System.exit(0);
						break;
					}
				}
				// System.out.println(appPath);
				// comprobar si existe la base de datos
				if (!new File(appPath + "DDBB").exists()) {
					System.err
							.println("No se encuentra la carpeta BBDD, porfavor genera el diccionario.");
					System.exit(0);
				}
				// hacer la busqueda en el diccionario
				String word = line.getOptionValue("w");

				Search search = new Search(word, type, silables, beggining,
						appPath);
				// Search search = new Search(word, Type, Silables,
				// Beggining,"/home/xerron/Documentos/Diccionarios/MERimas/");
				System.out.println("▼ Sustantivos y Adjetivos " + "("
						+ search.getSus().length + ")");
				for (int i = 1; i < search.getSus().length; i++) {
					System.out.println("    " + search.getSus()[i].getWord());
				}
				System.out.println("▼ Verbos " + "(" + search.getVerbs().length
						+ ")");
				for (int i = 1; i < search.getVerbs().length; i++) {
					System.out.println("    " + search.getVerbs()[i].getWord()
							+ " <" + search.getVerbs()[i].getOriginalWord()
							+ ">");
				}
				System.out.println("▼ Participios y formas enclípticas " + "("
						+ search.getRest().length + ")");
				for (int i = 1; i < search.getRest().length; i++) {
					System.out.println("    " + search.getRest()[i].getWord());
				}

			} else {
				System.err.println("Es necesario -w <palabra>");
				System.exit(0);
			}
		} catch (ParseException exp) {
			// oops, something went wrong
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
		}

	}

}
