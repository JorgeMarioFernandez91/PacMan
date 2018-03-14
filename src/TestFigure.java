public class TestFigure {

	private static GraphicalObject[] figures;
	private static Gui window;
	private static final int MOBILE_FIGURE = 1;// Figure that can be moved by
												// user
	private static final int FIGURE_KILLED = 4;// Figure destroyed
	private static final int SUCCESS = -2; // Figure was successfully moved
	private static final int HIT_BORDER = -1; // Figure could not move as it
	// hit the window's border

	/* ====================================== */
	public static void main(String args[]) {
		/* ====================================== */

		int numberFigures;
		final int progType = 2; // Type of figures controlled by the
								// computer

		int ncomp; // Number of figures controlled by the computer
		int[] progFig; // Figures controlled by computer
		int dir; // Direction in which computer figures move

		int code;
		int step = 40; // Length in pixels of each movement of a figure
		MoveFigure mover;

		window = new Gui(window.getWidth(), window.getHeight(), args[0]);
		mover = new MoveFigure(window);
		figures = window.getFigures();
		numberFigures = window.getNumFigures();
		progFig = new int[numberFigures];

		// Determine the figures controlled by the prorgam
		ncomp = 0;
		for (int i = 0; i < numberFigures; ++i) {
			if (figures[i].getType().equals("computer")) {
				dir = 1;// move right

				progFig[ncomp] = i;
				ncomp++;
			}
		}

		dir = 1;

		// Move figure around the window

		for (int i = 0; i < 10; ++i) {
			if ((code = mover.moveRight(figures, numberFigures, progFig[0], step)) != SUCCESS)
				dir = resolveCollision(code, dir);
			window.Wait();
		}

		for (int i = 0; i < 10; ++i) {
			if ((code = mover.moveDown(figures, numberFigures, progFig[0], step)) != SUCCESS)
				dir = resolveCollision(code, dir);
			window.Wait();
		}

		for (int i = 0; i < 10; ++i) {
			if ((code = mover.moveLeft(figures, numberFigures, progFig[0], step)) != SUCCESS)
				dir = resolveCollision(code, dir);
			window.Wait();
		}

		for (int i = 0; i < 10; ++i) {
			if ((code = mover.moveUp(figures, numberFigures, progFig[0], step)) != SUCCESS)
				dir = resolveCollision(code, dir);
			window.Wait();
		}

		System.out.println("Test is successfull if the figure did not");
		System.out.println("go outside the window");

		try {
			System.out.println("Press a key to continue");
			System.in.read();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (int i = 0; i < 5; ++i) {
			if ((code = mover.moveRight(figures, numberFigures, progFig[0], step)) != SUCCESS)
				dir = resolveCollision(code, dir);
			window.Wait();
		}

		for (int i = 0; i < 5; ++i) {
			if ((code = mover.moveDown(figures, numberFigures, progFig[0], 10)) != SUCCESS)
				dir = resolveCollision(code, dir);
			window.Wait();
		}

		if ((code = mover.moveLeft(figures, numberFigures, progFig[0], 20)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveDown(figures, numberFigures, progFig[0], 40)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveDown(figures, numberFigures, progFig[0], 40)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveRight(figures, numberFigures, progFig[0], 10)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveRight(figures, numberFigures, progFig[0], 40)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveLeft(figures, numberFigures, progFig[0], 50)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveLeft(figures, numberFigures, progFig[0], 50)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		if ((code = mover.moveLeft(figures, numberFigures, progFig[0], 50)) != SUCCESS)
			dir = resolveCollision(code, dir);
		window.Wait();

		System.out.println("The test is successful if the pac-man did not");
		System.out.println("overlap the blue wall and at the end it ate the");
		System.out.println("red ball.");

	}

	/* ======================================================= */
	private static int resolveCollision(int code, int dir) {
		/* ======================================================= */

		if ((code != HIT_BORDER) && (figures[code].getType().equals("computer"))) {
			/* Kill figure */
			try {
				for (int i = 0; i < 5; ++i) {
					window.drawFigure(figures[code]);
					Thread.sleep(200);
					window.eraseFigure(figures[code]);
					Thread.sleep(200);
				}

				figures[code].setOffset(new Location(-1000, -1000));
				figures[code].setType("killed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else if ((code != HIT_BORDER) && (figures[code].getType().equals("target"))) {
			window.eraseFigure(figures[code]);
			figures[code].setType("killed");
			figures[code].setOffset(new Location(-1000, -1000));
		}

		return dir;
	}
}
