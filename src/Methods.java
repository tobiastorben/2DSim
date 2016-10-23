import java.util.ArrayList;

public class Methods {

	public static void reverse(ArrayList<RigidBody> list) {

		for (int i = 0; i < (int) (list.size() / 2); i++) {
			RigidBody tmp = list.get(i);
			list.set(i, list.get(list.size() - i - 1));
			list.set(list.size() - i - 1, tmp);

		}

	}

	public static double max(double[] data) {
		double record = data[0];

		for (int i = 1; i < data.length; i++) {
			if (data[i] > record)
				record = data[i];
		}
		return record;
	}

	public static double min(double[] data) {
		double record = data[0];

		for (int i = 1; i < data.length; i++) {
			if (data[i] < record)
				record = data[i];
		}
		return record;
	}

	public static double length(double[] v) {
		return Math.sqrt(v[0] * v[0] + v[1] * v[1]);
	}

	public static double[] to3D(double[] v) {
		return new double[] { v[0], v[1], 0 };
	}

	public static double[] cross(double[] v1, double[] v2) {

		return new double[] { v1[1] * v2[2] - v1[2] * v2[1],
				v1[2] * v2[0] - v1[0] * v2[2], v1[0] * v2[1] - v1[1] * v2[0] };
	}

	public static double[] to2D(double[] v) {
		return new double[] { v[0], v[1] };
	}

	public static double planarCross(double[] v1, double[] v2) {

		return v1[0] * v2[1] - v1[1] * v2[0];

	}

	public static double[] rotate(double[] p, double ang) {
		return new double[] { p[0] * Math.cos(ang) - p[1] * Math.sin(ang),
				p[0] * Math.sin(ang) + p[1] * Math.cos(ang) };
	}

	public static double dot(double[] v1, double[] v2) {
		double dot = 0;
		for (int i = 0; i < v1.length; i++) {
			if (i == 2) {
				i = 3;
			}
			dot += v1[i] * v2[i];
		}
		return dot;

	}

	public static double[] times(double[] v, double k) {
		double[] v1 = new double[v.length];
		for (int i = 0; i < v.length; i++) {
			v1[i] = k * v[i];
		}
		return v1;
	}

	public static double[] add(double[] v1, double[] v2) {

		double[] v3 = new double[v1.length];

		for (int i = 0; i < v1.length; i++) {
			v3[i] = v1[i] + v2[i];
		}
		return v3;
	}

	public static double[] sub(double[] v1, double[] v2) {

		double[] v3 = new double[v1.length];

		for (int i = 0; i < v1.length; i++) {
			v3[i] = v1[i] - v2[i];
		}
		return v3;
	}

	public static double[] objectIntersection(SimObject obj1, SimObject obj2) {

		Combo pointC;
		double[] point;
		double[] corner = null;

		if (obj1.getType() == 1) {

			if (obj2.getType() == 1) {// Line-Line
				double[] lines1 = obj1.getLinesData();
				double[] lines2 = obj2.getLinesData();

				for (int i = 0; i < lines1.length; i += 4) {
					for (int k = 0; k < lines2.length; k += 4) {
						pointC = LLintersection(lines1[i], lines1[i + 1],
								lines1[i + 2], lines1[i + 3], lines2[k],
								lines2[k + 1], lines2[k + 2], lines2[k + 3]);
						if (pointC != null && pointC.isTrue())
							return pointC.getValues();
						if (pointC != null)
							corner = pointC.getValues();
					}

				}
				if (corner != null)
					return corner;
				return null;
				// return corner == null ? null : corner;
			}

			else { // Line-circle

				double[] circle = obj2.getCircleData();
				double[] lines = obj1.getLinesData();

				for (int i = 0; i < lines.length; i += 4) {
					point = CLintersect(lines[i], lines[i + 1], lines[i + 2],
							lines[i + 3], circle[0], circle[1], circle[2]);
					if (point != null)
						return point;
				}
			}

		} else {
			if (obj2.getType() == 1) { // Line-circle
				double[] circle = obj1.getCircleData();
				double[] lines = obj2.getLinesData();

				for (int i = 0; i < lines.length; i += 4) {
					point = CLintersect(lines[i], lines[i + 1], lines[i + 2],
							lines[i + 3], circle[0], circle[1], circle[2]);
					if (point != null)
						return point;
				}
			} else { // Circle-circle
				double[] circle1 = obj1.getCircleData();
				double[] circle2 = obj2.getCircleData();
				double[] r = { circle2[0] - circle1[0], circle2[1] - circle1[1] };
				double dist = Math.sqrt((circle1[0] - circle2[0])
						* (circle1[0] - circle2[0]) + (circle1[1] - circle2[1])
						* (circle1[1] - circle2[1]));
				if (dist <= (circle1[2] + circle2[2])) {
					return new double[] {
							circle1[0] + r[0] * (circle1[2] / dist),
							circle1[1] + r[1] * (circle1[2] / dist) };
				}

			}
		}
		return null;
	}

	public static Combo LLintersection(double x1, double y1, double x2,
			double y2, double x3, double y3, double x4, double y4) {
		double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (Math.abs(denom) <= 0.1) { // Lines are parallel.

			if (Math.abs(planarCross(new double[] { x3 - x1, y3 - y1 },
					new double[] { x2 - x1, y2 - y1 })) < 0.1) {

				if (Math.abs(y2 - y1) > Math.abs(x2 - x1)) {

					double[] top1, top2, bot1, bot2, p1, p2;

					if (y2 > y1) {
						top1 = new double[] { x2, y2 };
						bot1 = new double[] { x1, y1 };
					} else {
						top1 = new double[] { x1, y1 };
						bot1 = new double[] { x2, y2 };
					}

					if (y4 > y3) {
						top2 = new double[] { x4, y4 };
						bot2 = new double[] { x3, y3 };
					} else {
						top2 = new double[] { x3, y3 };
						bot2 = new double[] { x4, y4 };
					}

					if (max(new double[] { top1[0], top2[0] })
							- min(new double[] { bot2[0], bot1[0] }) <= top1[1]
							- bot1[1] + top2[1] - bot2[1]) {

						p1 = top1[1] > top2[1] ? top2 : top1;
						p2 = bot1[1] < bot2[1] ? bot2 : bot1;

						return new Combo(0, times(add(p1, p2), 0.5), true, null);
					}
				}

				else {

					double[] rigth1, left1, rigth2, left2, p1, p2;

					if (x2 > x1) {
						rigth1 = new double[] { x2, y2 };
						left1 = new double[] { x1, y1 };
					} else {
						rigth1 = new double[] { x1, y1 };
						left1 = new double[] { x2, y2 };
					}

					if (x4 > x3) {
						rigth2 = new double[] { x4, y4 };
						left2 = new double[] { x3, y3 };
					} else {
						rigth2 = new double[] { x3, y3 };
						left2 = new double[] { x4, y4 };
					}

					if (max(new double[] { rigth1[0], rigth2[0] })
							- min(new double[] { left2[0], left1[0] }) <= rigth1[0]
							- left1[0] + rigth2[0] - left2[0]) {

						p1 = rigth1[0] > rigth2[0] ? rigth2 : rigth1;
						p2 = left1[0] < left2[0] ? left2 : left1;

						return new Combo(0, times(add(p1, p2), 0.5), true, null);
					}
				}

			}
		}
		double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
		double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;
		if (ua >= 0.01f && ua <= 0.99f && ub >= 0.01f && ub <= 0.99f) {
			// Get the intersection point.
			return new Combo(0, new double[] { (x1 + ua * (x2 - x1)),
					(y1 + ua * (y2 - y1)) }, false, null);
		}

		return null;
	}

	public static double[] CLintersect(double Ax, double Ay, double Bx,
			double By, double Cx, double Cy, double radius) {

		if (length(new double[] { Ax - Cx, Ay - Cy }) <= radius)
			return new double[] { Ax, Ay };
		if (length(new double[] { Bx - Cx, By - Cy }) <= radius)
			return new double[] { Bx, By };

		double[] n = { Ay - By, Bx - Ax };
		n = dot(n, new double[] { Cx - Ax, Cy - Ay }) > 0 ? times(n, -1
				/ length(n)) : times(n, 1 / length(n));
		double[] v1 = { Ax - Cx, Ay - Cy };
		double[] v2 = { Bx - Cx, By - Cy };
		double[] v1z = sub(v1, times(n, dot(n, v1)));
		double[] v2z = sub(v2, times(n, dot(n, v2)));
		if (dot(v1z, v2z) < 0) {
			double[] l = { Bx - Ax, By - Ay };
			double[] unitL = times(l, 1 / length(l));
			double[] p = { Cx, Cy };
			double[] a = { Ax, Ay };
			double[] ict = add(a, (times(unitL, dot(unitL, sub(p, a)))));

			if (length(sub(ict, p)) <= radius)
				return ict;
		}

		return null;

	}

	public static double[] RKSolver(double[] x, double[] xDot, double dt) {

		int dim = x.length;

		double[] k2 = new double[dim];
		double[] k3 = new double[dim];
		double[] k4 = new double[dim];
		double[] newX = new double[dim];

		for (int i = 0; i < x.length; i++) {

			k2[i] = xDot[i] + 0.5 * xDot[i] * dt;
			k3[i] = xDot[i] + 0.5 * k2[i] * dt;
			k4[i] = xDot[i] + k3[i] * dt;

			newX[i] = x[i] + (dt / 6)
					* (xDot[i] + 2 * k2[i] + 2 * k3[i] + k4[i]);
		}
		return newX;
	}

	public static double RKSolver1(double x, double xDot, double dt) {

		double k2 = xDot + 0.5 * xDot * dt;
		double k3 = xDot + 0.5 * k2 * dt;
		double k4 = xDot + k3 * dt;

		return x + (dt / 6) * (xDot + 2 * k2 + 2 * k3 + k4);
	}

	// TODO : HANDLE MULTIPLE CONTACTS, FIX FOR CIRCULAR BODY SUPPORT
	public static void dynamics(Model mod, double t) {// TODO: Implement flexi
														// collision
		ArrayList<RigidBody> rigids = mod.getRigids();
		ArrayList<FlexibelBody> flexis = mod.getFlexis();
		ArrayList<StaticBody> statics = mod.getStatics();

		int i = 1;
		double[] point;
		RigidBody k;
		FlexibelBody f;

		for (SimObject o : mod.getObjs()) {
			o.init();
		}

		for (int b = 0; b < rigids.size(); b++) {
			RigidBody e;
			e = rigids.get(b);
			while (i < rigids.size()) {
				k = rigids.get(i);
				point = objectIntersection(e, k);
				if (point != null) {

					double[] r1 = { point[0] - e.getPos()[0],
							point[1] - e.getPos()[1] };
					double[] r2 = { point[0] - k.getPos()[0],
							point[1] - k.getPos()[1] };
					double[] velPoint1 = add(
							e.getVel(),
							cross(new double[] { 0, 0, e.getAngVel() },
									to3D(r1)));
					double[] velPoint2 = add(
							k.getVel(),
							cross(new double[] { 0, 0, k.getAngVel() },
									to3D(r2)));

					double[] n;

					if (e.getType() == 2 || k.getType() == 2) { // One is
																// circular

						double[] r;
						if (e.getType() == 2) {
							r = sub(point, e.getPos());
						} else {
							r = sub(point, k.getPos());
						}
						n = times(r, 1 / (length(r)));
					}

					else { // Both are polygons

						double[] lines1 = e.getLinesData();
						double[] lines2 = k.getLinesData();
						double[] p;
						double dist;
						double record1 = 1e15;
						double record2 = 1e15;

						for (int a = 0; a < lines1.length; a += 2) {
							p = sub(point, new double[] { lines1[a],
									lines1[a + 1] });
							dist = dot(p, p);
							if (dist < record1)
								record1 = dist;
						}
						for (int h = 0; h < lines2.length; h += 2) {
							p = sub(point, new double[] { lines2[h],
									lines2[h + 1] });
							dist = dot(p, p);
							if (dist < record2)
								record2 = dist;
						}
						if (record1 > record2) {
							n = e.getNormal(point);
						} else {
							n = k.getNormal(point);
						}
						if (dot(n, sub(k.getPos(), e.getPos())) < 0)
							n = times(n, -1);
					}

					double nDotVP1 = velPoint1[0] * n[0] + n[1] * velPoint1[1];
					double nDotVP2 = velPoint2[0] * n[0] + n[1] * velPoint2[1];
					double relVel = nDotVP2 - nDotVP1;

					

					if (relVel < 0) {

						dynCollisionResponse(e, k, point, n);
					}
				}
				i++;
			}
			for (StaticBody s : statics) {
				point = objectIntersection(e, s);
				if (point != null) {
					double[] r = sub(point, e.getPos());
					double[] velPoint = add(
							e.getVel(),
							cross(new double[] { 0, 0, e.getAngVel() }, to3D(r)));
					double[] n = s.getNormal(point);
					if (dot(n, r) < 0) {
						n = times(n, -1);
					}
					double relVel = -dot(n, velPoint);
					if (relVel < 0)
						s.staticCollisionResponse(e, point);
				}
			}

			for (FlexibelBody q : flexis) {

				Combo temp;
				double[] p = null;
				double[] lines2 = q.getLinesData();
				int idx = -1;
				double[] data = null;

				if (e.getType() == 1) {

					double[] lines1 = e.getLinesData();

					for (int z = 0; z < lines1.length; z += 4) {
						for (int x = 0; x < lines2.length; x += 4) {
							temp = LLintersection(lines1[z], lines1[z + 1],
									lines1[z + 2], lines1[z + 3], lines2[x],
									lines2[x + 1], lines2[x + 2], lines2[x + 3]);
							if (temp != null) {
								
								p = temp.getValues();
								idx = (int) ((x - 4) / 4);
								idx = idx < 0 ? 0 : idx;
								double[] pos1 = e.getPos();
								double[] vel1 = e.getVel();
								double[] vel2 = q.getElementVel().get(idx);
								double[] r = sub(p, pos1);
								double[] velPoint1 = add(
										vel1,
										cross(new double[] { 0, 0,
												e.getAngVel() }, to3D(r)));
								double[] relVel = sub(vel2, velPoint1);

								double[] n = { -q.getR().get(idx)[1],
										q.getR().get(idx)[0] };

								if (dot(n, r) < 0)
									n = times(n, -1);
								
								if (dot(relVel,n) < 0){
								double m1 = e.getMass();
								double m2 = q.getElementMass();
								
								
								
								double[] pos2 = q.getElementPos().get(idx);
								double meanCOR = (e.getCOR() + q.getCOR()) / 2;
								double I = e.getI();
								
								
								
								double[] T = { -n[1], n[0] };
								if (dot(T, relVel) < 0)
									T = times(T, -1);
								double c = dot(
										n,
										times(cross(cross(to3D(r), to3D(n)),
												to3D(r)), 1 / I));
								double Jr = -dot(n, sub(vel2, velPoint1))
										* ((meanCOR + 1) / ((1 / m1) + (1 / m2) + c));

								double myS1 = e.getMyS();
								double myS2 = q.getMyS();
								double myD1 = e.getMyD();
								double myD2 = q.getMyD();
								double Jf = 0;
								if (myS1 != 0 || myS2 != 0 || myD1 != 0
										|| myD2 != 0) {
									double myS = (myS1 + myS2) / 2;
									double myD = (myD1 + myD2) / 2;
									double Js = myS * Jr;
									double Jd = myD * Jr;
									if (dot(relVel, T) == 0.0f
											|| (m1 + m2) * dot(relVel, T) <= Js) {
										Jf = -(m1 + m2) * dot(relVel, T);
									} else {
										Jf = -Jd;
									}

								}

								double[] J = add(times(n, Jr), times(T, Jf));

								double[] newVel1 = sub(vel1, times(J, 1 / m1));
								double[] newVel2 = add(vel2, times(J, 1 / m2));
								double newAngVel1 = e.getAngVel()
										- (Jr * planarCross(r, n) + Jf
												* planarCross(r, T)) / I;
								e.setVel(newVel1);
								e.setAngVel(newAngVel1);
								q.getElementVel().set(idx, newVel2);
								}
							}
						}
					}
				} else if (e.getType() == 2) {
					data = e.getCircleData();
					for (int x = 0; x < lines2.length; x += 4) {
						p = CLintersect(lines2[x], lines2[x + 1],
								lines2[x + 2], lines2[x + 3], data[0], data[1],
								data[2]);
						if (p != null) {
							idx = (int) ((x - 4) / 4);
							idx = idx < 0 ? 0 : idx;
							double[] pos1 = e.getPos();
							double[] vel1 = e.getVel();
							double[] vel2 = q.getElementVel().get(idx);
							double[] r = sub(p, pos1);
							double[] velPoint1 = add(
									vel1,
									cross(new double[] { 0, 0,
											e.getAngVel() }, to3D(r)));
							double[] relVel = sub(vel2, velPoint1);

							double[] n = { -q.getR().get(idx)[1],
									q.getR().get(idx)[0] };

							if (dot(n, r) < 0)
								n = times(n, -1);
							
							if (dot(relVel,n) < 0){
							double m1 = e.getMass();
							double m2 = q.getElementMass();
							
							
							
							double[] pos2 = q.getElementPos().get(idx);
							double meanCOR = (e.getCOR() + q.getCOR()) / 2;
							double I = e.getI();
							
							
							
							double[] T = { -n[1], n[0] };
							if (dot(T, relVel) < 0)
								T = times(T, -1);
							double c = dot(
									n,
									times(cross(cross(to3D(r), to3D(n)),
											to3D(r)), 1 / I));
							double Jr = -dot(n, sub(vel2, velPoint1))
									* ((meanCOR + 1) / ((1 / m1) + (1 / m2) + c));

							double myS1 = e.getMyS();
							double myS2 = q.getMyS();
							double myD1 = e.getMyD();
							double myD2 = q.getMyD();
							double Jf = 0;
							if (myS1 != 0 || myS2 != 0 || myD1 != 0
									|| myD2 != 0) {
								double myS = (myS1 + myS2) / 2;
								double myD = (myD1 + myD2) / 2;
								double Js = myS * Jr;
								double Jd = myD * Jr;
								if (dot(relVel, T) == 0.0f
										|| (m1 + m2) * dot(relVel, T) <= Js) {
									Jf = -(m1 + m2) * dot(relVel, T);
								} else {
									Jf = -Jd;
								}

							}

							double[] J = add(times(n, Jr), times(T, Jf));

							double[] newVel1 = sub(vel1, times(J, 1 / m1));
							double[] newVel2 = add(vel2, times(J, 1 / m2));
							double newAngVel1 = e.getAngVel()
									- (Jr * planarCross(r, n) + Jf
											* planarCross(r, T)) / I;
							e.setVel(newVel1);
							e.setAngVel(newAngVel1);
							q.getElementVel().set(idx, newVel2);
							}
					}
					}
				}

			}

			i = b + 2;
		}

		double[] F;
		double M;
		// Force/Moment calculation and state integration
		for (RigidBody e : rigids) {

			e.calcForce(t);
			F = e.getSigmaF();
			M = e.getSigmaM();

			e.setVel(RKSolver(e.getVel(), new double[] { F[0] / e.getMass(),
					F[1] / e.getMass() }, mod.getStep()));
			e.setPos(RKSolver(e.getPos(), e.getVel(), mod.getStep()));
			e.setAngVel(RKSolver1(e.getAngVel(), M / e.getI(), mod.getStep()));
			e.setAng(RKSolver1(e.getAng(), e.getAngVel(), mod.getStep()));

		}

		for (FlexibelBody g : flexis) {

			g.calcForce(t);

			for (int p = 0; p < g.getNumElems() - 1; p++) {

				g.getElementVel().set(
						p,
						Methods.times(Methods.RKSolver(
								g.getElementVel().get(p), Methods.times(g
										.getLoads().get(p), 1 / g
										.getElementMass()), g.getMod()
										.getStep()), g.getDamping()));

				g.getElementPos().set(
						p,
						Methods.RKSolver(g.getElementPos().get(p), g
								.getElementVel().get(p), g.getMod().getStep()));

			}
			

		}

	}

	public static void simLoop(Model mod) {
		// TODO: IMPLEMENT
	}

	public static void dynCollisionResponse(RigidBody obj1, RigidBody obj2,
			double[] ict, double[] n) {
		double[] vel1 = obj1.getVel();
		double[] vel2 = obj2.getVel();
		double[] pos1 = obj1.getPos();
		double[] pos2 = obj2.getPos();
		double meanCOR = (obj1.getCOR() + obj2.getCOR()) / 2;
		double I1 = obj1.getI();
		double I2 = obj2.getI();
		double[] r1 = sub(ict, pos1);
		double[] r2 = sub(ict, pos2);

		double[] velPoint1 = add(vel1,
				cross(new double[] { 0, 0, obj1.getAngVel() }, to3D(r1)));
		double[] velPoint2 = add(vel2,
				cross(new double[] { 0, 0, obj2.getAngVel() }, to3D(r2)));
		double[] relVel = sub(velPoint2, velPoint1);
		double[] T = { -n[1], n[0] };
		if (dot(T, relVel) < 0)
			T = times(T, -1);
		double c = dot(
				n,
				add(times(cross(cross(to3D(r1), to3D(n)), to3D(r1)), 1 / I1),
						times(cross(cross(to3D(r2), to3D(n)), to3D(r2)), 1 / I2)));

		double Jr = -dot(n, sub(velPoint2, velPoint1))
				* ((meanCOR + 1) / ((1 / obj1.getMass()) + (1 / obj2.getMass()) + c));

		double myS1 = obj1.getMyS();
		double myS2 = obj2.getMyS();
		double myD1 = obj1.getMyD();
		double myD2 = obj2.getMyD();
		double Jf = 0;
		if (myS1 != 0 || myS2 != 0 || myD1 != 0 || myD2 != 0) {
			double myS = (myS1 + myS2) / 2;
			double myD = (myD1 + myD2) / 2;
			double Js = myS * Jr;
			double Jd = myD * Jr;
			if (dot(relVel, T) == 0.0f
					|| (obj1.getMass() + obj2.getMass()) * dot(relVel, T) <= Js) {
				Jf = -(obj1.getMass() + obj2.getMass()) * dot(relVel, T);
			} else {
				Jf = -Jd;
			}

		}

		double[] J = add(times(n, Jr), times(T, Jf));

		double[] newVel1 = sub(vel1, times(J, 1 / obj1.getMass()));
		double[] newVel2 = add(vel2, times(J, 1 / obj2.getMass()));
		double newAngVel1 = obj1.getAngVel()
				- (Jr * planarCross(r1, n) + Jf * planarCross(r1, T)) / I1;
		double newAngVel2 = obj2.getAngVel()
				+ (Jr * planarCross(r2, n) + Jf * planarCross(r2, T)) / I2;
		obj1.setVel(newVel1);
		obj1.setAngVel(newAngVel1);
		obj2.setVel(newVel2);
		obj2.setAngVel(newAngVel2);

	}

	public static double[] getUnitNormal(double x1, double y1, double x2,
			double y2) {
		double[] n = { y2 - y1, x1 - x2 };
		double length = Math.sqrt(n[0] * n[0] + n[1] * n[1]);

		if (length == 0) {
			double[] r = { x2 - x1, y2 - y1 };
			double[] newP = add(new double[] { x1, x2 }, times(r, 5));
			return getUnitNormal(x1, y1, newP[0], newP[1]);
		}

		return new double[] { n[0] / length, n[1] / length };
	}
}
