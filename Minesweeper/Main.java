/* Copyright 2019 William Chan and Peter Lim
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Minesweeper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.commons.lang3.time.StopWatch; //stopwatch class taken here
import java.util.concurrent.TimeUnit; //class to count stopwatch class taken here
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		char value;
		Scanner input = new Scanner(System.in);
		boolean menuFlag = true;
		boolean gameRunning = true;

		do {
			printIntro();

			while (menuFlag) {

				StopWatch timer = new StopWatch();
				boolean lose = false;
				boolean win = false;
				int choice = 0;
				char difficulty = '0';

				// main menu
				System.out.println("\n1: Play Game");
				System.out.println("2: View Scoreboard");
				System.out.print("\nEnter input or (e) to exit: ");
				String s = input.next();

				// checks if input is correct
				value = InputValidator.isChar(s);

				if (Character.toLowerCase(value) == 'e') {
					System.out.println("Exit");
					menuFlag = false;
					gameRunning = false;

				} else if (Character.toString(value).equals("1") || Character.toString(value).equals("2")) {
					choice = Integer.parseInt(Character.toString(value));

				} else {
					System.out.println("Invalid input, try again.");

				}

				if (choice == 1) {
					MineField m = null;

					System.out.println("(e)asy");
					System.out.println("(m)eadium");
					System.out.println("(h)ard");
					System.out.println("(c)ustom");
					System.out.print("Enter input: ");

					value = 'n';
					while (value == 'n') {
						s = input.nextLine();
						value = InputValidator.isChar(s);
						if (value == 'e') {
							m = new MineField(10, 18, 10);
							difficulty = 'e';
							timer.start();
						} else if (value == 'm') {
							m = new MineField(14, 18, 40);
							difficulty = 'm';
							timer.start();
						} else if (value == 'h') {
							m = new MineField(20, 24, 99);
							difficulty = 'h';
							timer.start();
						} else if (value == 'c') {

							int collength = 0;
							int rowlength = 0;
							int numOfMines = 0;

							do {

								try {
									System.out.print("row length: ");
									rowlength = Integer.parseInt(input.next());

								} catch (NumberFormatException e) {
									rowlength = -1;
								}

							} while (rowlength < 3 || rowlength > 26);

							do {

								try {
									System.out.print("column length: ");
									collength = Integer.parseInt(input.next());

								} catch (NumberFormatException e) {
									collength = -1;
								}

							} while (collength < 3 || collength > 26);

							do {

								try {
									System.out.print("num of mines: ");
									numOfMines = Integer.parseInt(input.next());

								} catch (NumberFormatException e) {
									collength = -1;
								}

							} while (numOfMines < 1 || collength > rowlength * collength);
							m = new MineField(rowlength, collength, numOfMines);
							timer.start();
						} else {
							value = 'n';
						}
					}

					while (!lose && !win) {
						System.out.println(m);
						System.out.println("\nR: Reveal");
						System.out.println("F: Flag");
						System.out.println("U:unflag ");
						System.out.print("enter input:");
						String z = input.next().toLowerCase();

						switch (z) {

						case "r":
							lose = m.uncover();
							break;

						case "f":
							win = m.flag();
							break;

						case "u":
							m.unFlag();
							break;
						case "rall":
							System.out.println(m.reveal());
							break;
						}

					}

					if (win) {
						System.out.println("\nYou win");
						timer.stop();
						System.out.println("Done in " + TimeUnit.MILLISECONDS.toSeconds(timer.getTime()) + " seconds");
						System.out.print("Enter your name: ");
						input.nextLine();
						String name = input.nextLine();
						recordTime(name, timer, difficulty);
					} else if (lose) {
						System.out.println("\nYou lose");
						timer.stop();

					}

				} else if (choice == 2) {
					System.out.println("\nScores: ");
					viewScores();
				}

			}
			;

		} while (gameRunning);
		// choice = Integer.parseInt(input.readLine());

	}

	private static void printIntro() {
		System.out.println("  __  __ _                                                   \r\n"
				+ " |  \\/  (_)                                                  \r\n"
				+ " | \\  / |_ _ __   ___  _____      _____  ___ _ __   ___ _ __ \r\n"
				+ " | |\\/| | | '_ \\ / _ \\/ __\\ \\ /\\ / / _ \\/ _ \\ '_ \\ / _ \\ '__|\r\n"
				+ " | |  | | | | | |  __/\\__ \\\\ V  V /  __/  __/ |_) |  __/ |   \r\n"
				+ " |_|  |_|_|_| |_|\\___||___/ \\_/\\_/ \\___|\\___| .__/ \\___|_|   \r\n"
				+ "                                            | |              \r\n"
				+ "                                            |_|              ");
	}

	private static void recordTime(String name, StopWatch timer, char difficulty) throws Exception {

		try (FileWriter f = new FileWriter("scores" + difficulty + ".txt", true);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter p = new PrintWriter(b);) {

			p.println(name + "-" + timer.getTime() + "-" + difficulty);

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	private static void viewScores() {

		String cwd = System.getProperty("user.dir");
		
		// may need to change these
		File easy = new File(cwd + "\\scorese.txt");
		File med = new File(cwd + "\\scoresm.txt");
		File hard = new File(cwd + "\\scoresh.txt");

		BufferedReader br = null;

		try {
			System.out.println("\nEASY: ");
			ArrayList<PlayerScore> listOfScoresE = new ArrayList<>();
			br = new BufferedReader(new FileReader(easy));
			String st;

			while ((st = br.readLine()) != null) {
				String tokens[] = st.split("-");
				listOfScoresE.add(new PlayerScore(tokens[0], Long.parseLong(tokens[1])));
			}

			Collections.sort(listOfScoresE);

			for (PlayerScore P : listOfScoresE) {
				System.out.println(P);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		try {
			System.out.println("\nMEDIUM: ");
			ArrayList<PlayerScore> listOfScoresM = new ArrayList<>();
			br = new BufferedReader(new FileReader(med));
			String st;

			while ((st = br.readLine()) != null) {
				String tokens[] = st.split("-");
				listOfScoresM.add(new PlayerScore(tokens[0], Long.parseLong(tokens[1])));
			}

			Collections.sort(listOfScoresM);

			for (PlayerScore m : listOfScoresM) {
				System.out.println(m);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		try {
			System.out.println("\nHARD: ");
			ArrayList<PlayerScore> listOfScoresH = new ArrayList<>();
			br = new BufferedReader(new FileReader(hard));
			String st;

			while ((st = br.readLine()) != null) {
				String tokens[] = st.split("-");
				listOfScoresH.add(new PlayerScore(tokens[0], Long.parseLong(tokens[1])));
			}

			Collections.sort(listOfScoresH);

			for (PlayerScore x : listOfScoresH) {
				System.out.println(x);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		System.out.println("\n\n----------------------------------------------------------------");

	}

}
