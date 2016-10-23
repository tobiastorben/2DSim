import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Simulation {

	public static void main(String[] args) {
		// --------Simulation input section-------- \\
		
		// Simulation objects
		double[] initPos1 ={0,-14};
		double[] initVel1 = {0,30};
		double[] initPos2 = {0, -18};
		double[] initVel2 = {0, 0};
		double[] initPos3 = {-9,-15};
		double[] initVel3 = {0,0};
		double[] initPos4 = {15,0};
		double[] initVel4 = {-10,0};
		double[] initPos5 = {-11,-15};
		double[] initVel5 = {4,10};
		double[] initPos6 = {9,13};
		double[] initVel6 = {10,-10};
		double[] initPos7 = {-10,-10};
		double[] initVel7 = {0,-10};
		double[] initPos8 = {-15,-15};
		double[] initVel8 = {10,30};
		double[] initPos9 = {-2,-2};
		double[] initVel9 = {20,10};
		double[] initPos10 = {-15,0};
		double[] initVel10 = {10,0};
		double[] initPos11 = {-5,-10};
		double[] initVel11 = {5,0};
		double[] initPos12 = {0,-10};
		double[] initVel12 = {14,-1};
		double[] initPos13 = {0,10};
		double[] initVel13 = {0,0};
		double[] initPos14 = {0,-15.5};
		double[] initVel14 = {0,0};
		double[] initPos15 = {-10,-10};
		double[] initVel15 = {5,0};
		double[] initPos16 = {-8,10};
		double[] initVel16 = {5,0};
		
		double[] p1 = {20,-20};
		double[] p2 = {20,20};
		double[] p3 = {-20, 20};
		double[] p4 = {-20, -20};
		double[] p5 = {-15,-15};
		double[] p6 = {10,15};
		
		double[] end1 = {0,0};
		double[] end2 = {1/Math.sqrt(2)+0.866025+1+0.866025 + 1/Math.sqrt(2),0};
		
		double[] c1 = {0,-1};
		double[] c2 = {1/Math.sqrt(2), -1/Math.sqrt(2)-1};
		double[] c3 = {1/Math.sqrt(2)+0.866025,-1/Math.sqrt(2)-0.5-1};
		double[] c4 = {1/Math.sqrt(2)+0.866025+0.5,-1/Math.sqrt(2)-0.5-1-0.866025};
		double[] c5 = {1/Math.sqrt(2)+0.866025+1,-1/Math.sqrt(2)-0.5-1};
		double[] c6 = {1/Math.sqrt(2)+0.866025+1+0.866025,-1/Math.sqrt(2)-1};
		double[] c7 = {1/Math.sqrt(2)+0.866025+1+0.866025 + 1/Math.sqrt(2),-1};
		double[] c8 = {1/Math.sqrt(2)+0.866025+1+0.866025 + 1/Math.sqrt(2),0};
		double[] c9 = {3/Math.sqrt(2), 3/Math.sqrt(2) + 3};
		double[] velC1 = {0,0};
		double[] velC2 = {0,0};
		double[] velC3 = {0,0};
		double[] velC4 = {0,0};
		double[] velC5 = {0,0};
		double[] velC6 = {0,0};
		double[] velC7 = {0,0};
		double[] velC8 = {0,0};
		double[] pl1 = {0,0};
		double[] pl2 = {0,0};
		double[] pl3 = {0,0};
		double[] pl4 = {0,-10};
		double[] pl5 = {0,0};
		double[] pl6 = {0,0};
		double[] pl7 = {0,0};
		double[] pl8 = {0,0};
		ArrayList<double[]> pointLoads1 = new ArrayList<double[]>(2);
		pointLoads1.add(pl1);
		pointLoads1.add(pl2);
		pointLoads1.add(pl3);
		pointLoads1.add(pl4);
		pointLoads1.add(pl5);
		pointLoads1.add(pl6);
		pointLoads1.add(pl7);
		ArrayList<double[]> pointLoads2 = new ArrayList<double[]>(1);
		pointLoads2.add(new double[] {94, 0, 0});
		

		
		
		
		
		ArrayList<double[]> initElementPos = new ArrayList<double[]>(2);
		initElementPos.add(c1);
		initElementPos.add(c2);
		initElementPos.add(c3);
		initElementPos.add(c4);
		initElementPos.add(c5);
		initElementPos.add(c6);
		initElementPos.add(c7);
		
		
		ArrayList<double[]> initElementVel = new ArrayList<double[]>(2);
		initElementVel.add(velC1);
		initElementVel.add(velC2);
		initElementVel.add(velC3);
		initElementVel.add(velC4);
		initElementVel.add(velC5);
		initElementVel.add(velC6);
		initElementVel.add(velC7);
		
		double COR = 0.9;
		
		Plane plane1 = new Plane(p1,p2,Color.BLACK);
		Plane plane2 = new Plane(p2,p3,Color.BLACK);
		Plane plane3 = new Plane(p3,p4,Color.BLACK);
		Plane plane4 = new Plane(p4,p1,Color.BLACK);
		Plane plane5 = new Plane(p5,p6,Color.BLACK);
		ChargedBall ball1 = new ChargedBall(1000, 0, 2,COR,0.0,0.0, initPos1, initVel1,0,0, true, Color.PINK);
		ChargedBall ball2 = new ChargedBall(1000, 0, 2,COR,0.6,0.5, initPos2, initVel2,0,0,  true, Color.RED);
		ChargedBall ball3 = new ChargedBall(1000, 0, 2,COR,0.6,0.5, initPos3, initVel3,0,0,  true, Color.BLACK);
		ChargedBall ball4 = new ChargedBall(1000, 0, 2,COR,0.6,0.5, initPos4, initVel4,0,0,  true, Color.WHITE);
		ChargedBall ball5 = new ChargedBall(1000, 0, 2,COR,0.6,0.5, initPos5, initVel5,0,0,  true, Color.ORANGE);
		ChargedBall ball6 = new ChargedBall(1000, 0, 2,COR,0.6,0.5,initPos6, initVel6,0,0,  true, Color.PINK);
		ChargedBall ball7 = new ChargedBall(1000, 0, 2,COR,0.8,0.5,initPos7, initVel7,0,10,  true, Color.MAGENTA);
		ChargedBall ball8 = new ChargedBall(1000, 0, 2,COR,0.8,0.5,initPos8, initVel8,0,0,  true, Color.DARK_GRAY);
		ChargedBall ball9 = new ChargedBall(1000, 0, 4, COR,0.8,0.5,initPos9, initVel9,0,0,  true, Color.WHITE);
		ChargedBall ball10 = new ChargedBall(1000, 0, 2,COR,0.8,0.5,initPos10, initVel10,0,-3,  true, Color.YELLOW);
		ChargedBall ball11 = new ChargedBall(1000, 0, 1, COR,0.8,0.5,initPos11, initVel11,0,3,  true, Color.GRAY);
		
		Box box1 = new Box(1000, 3, 3, initPos12, initVel12,0.2,0,COR,0,0,Color.YELLOW);
		Box box2 = new Box(1000, 3, 3, initPos13, initVel13, 0,0,COR,0.6,0.5,Color.BLUE);
		Box box3 = new Box(1000, 20, 1, initPos14, initVel14, 0,3,COR,0.6,0.5,Color.BLACK);
		Box box4 = new Box(1000, 3, 3, initPos15, initVel15, 0,2,COR,0.6,0.5,Color.RED);
		Box box5 = new Box(1000, 3, 3, initPos16, initVel16, 0,1,COR,0.6,0.5,Color.CYAN);
		
		
		
		Cable cable1 = new Cable(100,end1,end2,initElementPos,initElementVel, pointLoads1, 1, 0,0, 0.999, 0.0314, 210e9,7.85e-5,  Color.BLACK);
		Cable cable2 = new Cable(10000, -10, 10,
				null, null,
				1, 0, 0, 0.9992, 0.0000009, 210e9,0.00000003,  Color.BLACK, 0.4,
				-0.1,0,20);
		Cable cable3 = new Cable(1000, -10, 0, 100,
				null, null,
				1, 0, 0, 0.9999, 0.000314, 210e9,0,1000,   Color.BLACK, 0.3);
		
		
		ConstantGravity gravity = new ConstantGravity(0,-9.81);
		ArrayList<SimObject>simObjs = new ArrayList<SimObject>();
////		simObjs.add(ball1);
////		simObjs.add(ball2);
////		simObjs.add(ball3);
////		simObjs.add(ball4);
////		simObjs.add(ball5);
////		simObjs.add(ball6);
		simObjs.add(ball7);
		simObjs.add(ball8);
		simObjs.add(ball9);
//		simObjs.add(ball10);
//		simObjs.add(ball11);
//		
//	simObjs.add(box1);
//		simObjs.add(box2);
		simObjs.add(box3);
//		//simObjs.add(box4);
//		simObjs.add(box5);
////		
		simObjs.add(plane1);
		simObjs.add(plane2);
		simObjs.add(plane3);
		simObjs.add(plane4);
		//simObjs.add(plane5);
		

//		simObjs.add(cable1);
		simObjs.add(cable2);
//		simObjs.add(cable3);
		
		simObjs.add(gravity);
	
		
		
		
		Model mod= new Model(simObjs, 0.001, 60, 1, 15, true, 800, 800);
		

		// ----------------------------------------- \\
		
		for (SimObject e : mod.getObjs()) {
			e.init();
			e.setMod(mod);
			}
		

		if (mod.isVis()) {
			JFrame frame = new JFrame("2D Physics Simulator");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize((int) mod.getWinWidth(),(int) mod.getWinHeigth());
			frame.setLocationRelativeTo(null);
			frame.add(new VisualSim(mod));
			frame.setVisible(true);
			return;
		}

		else {
			Methods.simLoop(mod);
		}
	}
}

