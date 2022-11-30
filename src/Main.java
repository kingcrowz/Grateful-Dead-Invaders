import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;
import java.applet.*;
import java.net.*;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JApplet;
//HI ZACH!

@SuppressWarnings("serial")
public class Main extends JApplet implements Runnable, MouseMotionListener,MouseListener,KeyListener{
	Random generator=new Random();
	boolean GameRunning=true,PlayAgain=true;
	double saver=0;
	boolean music=false;	
	public int rando;
	public int scoore=0;
	boolean touched=false;
	boolean circles=false;
	int width,height;
	int score=0;
	double savedscore=0;
	Image offscreenImage;


	int cococo=0;
	String location = "File:/Network/Servers/student10.eccrsd.us/Homes/zacharyzaleski/Desktop/Ripple.wav";
	
	
	
//Player
///////Health
	int Health=5;
	Image Wilmer;
////////Position
	int XX;
	int XY;
	boolean left=false;
	boolean right=false;
	boolean up=false;
	boolean down=false;
////////Shot
	Image Shot;
	boolean shoot=false;
	int shotX=1000, shotY=1000;
	int shotDelay=0;
	boolean shoot2=false;
	int shot2X=1000, shot2Y=1000;
	int shot2Delay=0;
////////Ballista	
	boolean ballista=false;
	int ballistaRemaining=1;
	Image Ballista;



	Graphics offscr;
	Random r;
	Thread t;
	boolean inBounds=true;
	boolean onScreen=true;
	boolean playNormal=true, playSeeker=false;
//BKG(s)
	Image Title;
	Image BKG;
//Level 10
////////attacks
//Phase 1
	int rain1X;
	int rain1Y;
	int rain1Count=0;
	int rain2X;
	int rain2Y;
	int rain2Count=0;
//Phase 2
	boolean L10R1 = false;
	boolean L10R2 = false;
	boolean L10R3 = false;
/////Ripple
	int RippleCount=1;
	int rippleFreq=1000;
	int RippleX=0;
	int StunCount=1000;
	boolean Rippler=false;
/////Bond
	boolean bond = false;
////BeardGuard
	int BeardCount=1;
	Image BeardGuard;
	Image BarberPole;
	int poleHealth=5;
	Image BeardWarning;
	boolean Beard = false;
////////Imgs
	Image Rose;
	Image Bond;
	Image Keyboard;
	Image Ripple;
	Image L10P1Warning;
	Image L10P2Shot;

	boolean hasStarted=false;
//Boss 1
////////Mobs
	boolean[] Boss1MobsAlive;
	int[] B1MX;
	int[] B1MY;
	int B1Mobs=0;
	int SpacerCount=0;
////////Flak
	Image Flak;
	int[] flakX;		
	int numOfFlak=0;
	boolean[] flak;
	int[] flakCount;
	Image warning;
////////Strafe
	int[] Strafe;
	int StrafeY=0;
	boolean[] StrafeHole;
	boolean StrafeActive = false;
//Invaders
////////Alive?
	boolean[][] InvadersAlive;
	int[][] InvadersHealth;
	int RC=0, LC=0;
	Image[][] Invaders;		
////////Direction?
	boolean InvDir=true;
	int iX=0, iY=0;
////////Enemy Fire
	boolean[][] InvadersShot;
	Image eShot;
	int[][] InvadersShotX;
	int[][] InvadersShotY;
	int shotSpeed=4, shotChance=5000;
	boolean BrokenArrow=false;
//Levels?
	int numDead=0;
	int level=1;

	public Main()
	{
		r=new Random();
		t=new Thread(this);
		t.start();
	}
	public void init()
	{
		//		rain1X = new int[1000000];
		//		rain2X = new int[1000000];
		//		rain1Y = new int[1000000];
		//		rain2Y = new int[1000000];
		flakCount = new int[100];
		Boss1MobsAlive = new boolean[1000];
		B1MX = new int[1000];
		B1MY = new int[1000];
		StrafeHole = new boolean[50];
		Strafe = new int[50];
		Invaders = new Image[5][10];
		InvadersAlive = new boolean[5][10];
		InvadersShot = new boolean[5][10];
		InvadersHealth = new int[5][10];
		InvadersShotX = new int[5][10];
		InvadersShotY = new int[5][10];
		flakX = new int[15];
		flak = new boolean[15];
		XX=700;
		XY=0;
		saver=0;
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		setVisible(true);
		setSize(1000, 800);
		width = getSize().width;
		height = getSize().height;
		BarberPole = getImage(getCodeBase(), "GDIBarberPole.png");
		BeardWarning = getImage(getCodeBase(), "GDIBeardWarning.png");
		BeardGuard = getImage(getCodeBase(), "WeirBeardGuard.png");
		L10P2Shot = getImage(getCodeBase(), "L10P2Shot.png");
		L10P1Warning = getImage(getCodeBase(), "L10P1Warning.png");
		Ripple = getImage(getCodeBase(), "BassAttack.png");
		Keyboard = getImage(getCodeBase(), "KeyboardGuard.png");
		Bond = getImage(getCodeBase(), "FurthurBond.png");
		Rose = getImage(getCodeBase(), "Rose.png");
		warning = getImage(getCodeBase(), "GDIwarning.png");
		Ballista = getImage(getCodeBase(), "Ballista.png");
		Flak = getImage(getCodeBase(), "GDIflakfire.png");
		Wilmer = getImage(getCodeBase(), "GratefulInvaders.png");
		BKG = getImage(getCodeBase(), "Title.png");
		Title = getImage(getCodeBase(), "GDItitle.png");
		eShot = getImage(getCodeBase(), "GDIcuffs.png");
		for(int count=0; count<1000; count++)
		{
			Boss1MobsAlive[count] = false;
			B1MX[count] = 0;
			B1MY[count] = 0;
		}
		for(int count=0; count<50; count++)
		{
			Strafe[count] = (count+1) * 20;
			StrafeHole[count] = false;
		}
		while(LC<=4)
		{
			while(RC<=9)
			{
				flakCount[RC]=0;
				flakX[RC]=0;
				flak[RC]=true;
				InvadersShot[LC][RC] = false;
				InvadersHealth[LC][RC] = 1;
				InvadersAlive[LC][RC] = true;
				Invaders[LC][RC] = getImage(getCodeBase(), "GreenBear.png");
				RC++;
			}
			RC=0;
			LC++;
		}
//				InvadersAlive[0][0] = true;
//				InvadersHealth[0][0] = 50;
		level=1;
//		numDead=50;
		Shot=getImage(getCodeBase(), "GratefulShot.png");
	}

	public void paint(Graphics g)
	{
		DecimalFormat df=new DecimalFormat("0.00");
		Random generator=new Random();
		rain1Count++;
		if (hasStarted)
		{
			if (inBounds && Health>0)
			{
				if(cococo==0)
				{
					this.playSound(location);
					cococo++;
				}
				//				System.out.println(rain1Count);
				if(Health<=4)
				{
					Wilmer = getImage(getCodeBase(), "GratefulInvaders4HP.png");
					if(Health<=3)
					{
						Wilmer = getImage(getCodeBase(), "GratefulInvaders3HP.png");
						if(Health<=2)
						{
							Wilmer = getImage(getCodeBase(), "GratefulInvaders2HP.png");
							if(Health==1)
							{
								Wilmer = getImage(getCodeBase(), "GratefulInvaders1HP.png");
							}
						}
					}
				}
				if(level==5 && (InvadersHealth[0][0]<45 && InvadersHealth[0][0]>=30))
					Invaders[0][0] = getImage(getCodeBase(), "GDIboss1DMG1.png");
				if(level==5 && (InvadersHealth[0][0]<30))
					Invaders[0][0] = getImage(getCodeBase(), "GDIboss1DMG2.png");
				super.paint(g);
				g.drawImage(BKG, 0, 0, this);
				if(level==5)
				{
					setSize(1200, 1000);
					if(up==true && XY>=5)
						XY=XY-5;
					if(down==true && XY<=745)
						XY=XY+5;
				}
				if(left==true && XX+5>0 && StunCount>=250)
				{
					XX=XX-5;
				}
				if(right==true && XX-5<=950 && level!=10)
				{
					XX=XX+5;
				}
				if(right==true && XX-5<=1150 && level==10 && StunCount>=250)
				{
					XX=XX+5;
				}
				if(shoot==true && ballista==false)
				{
					if(shotDelay==0)
					{
						shotX=XX;
						shotY=650;
						scoore--;
					}
					g.drawImage(Shot, shotX, shotY, this);
					shotY=shotY-6;
					shotDelay++;
					if(shotY<=-50)
					{
						shoot=false;
						shotDelay=0;
					}
				}
				if(shoot2)
				{
					if(shot2Delay==0)
					{
						shot2X=XX+75;
						shot2Y=650;
						scoore--;
					}
					g.drawImage(Shot, shot2X, shot2Y, this);
					shot2Y=shot2Y-6;
					shot2Delay++;
					if(shot2Y<=-50)
					{
						shoot2=false;
						shot2Delay=0;
					}
				}
				//BALLISTA
				if(ballista==true && shoot==false)
				{
					if(shotDelay==0)
					{
						shotX=XX;
						shotY=650;
					}
					g.drawImage(Ballista, shotX, shotY, this);
					shotY=shotY-10;
					shotDelay++;
					{
						if(shotY<0)
						{
							ballista=false;
							shotDelay=0;
						}
					}
				}
				LC=0;
				RC=0;
//Level 10
				if(level==10)
				{
					setSize(1200, 1000);
//////////////DRUMMERS
					if((InvadersAlive[3][0] || InvadersAlive[3][9]) && L10R1)
					{
//						InvadersAlive[3][0]=false;
//						InvadersAlive[3][9]=false;
						if(InvadersAlive[3][0] && InvadersAlive[3][9])
							g.drawImage(L10P1Warning, 100, 800, this);
						if(iX>=640 || iX<=0)
						{
							if(InvadersAlive[3][0] && InvadersAlive[3][9] && L10R1)
							{
								InvadersAlive[4][2] = true;
								InvadersHealth[4][2] = 1;
								InvadersAlive[4][3] = true;
								InvadersHealth[4][3] = 1;
								InvadersAlive[4][4] = true;
								InvadersHealth[4][4] = 1;
								InvadersAlive[4][5] = true;
								InvadersHealth[4][5] = 1;
								InvadersAlive[4][6] = true;
								InvadersHealth[4][6] = 1;
								InvadersAlive[4][7] = true;
								InvadersHealth[4][7] = 1;
								InvadersAlive[4][8] = true;
								InvadersHealth[4][8] = 1;
							}
						}
//shot1
						if((shotX>=(iX-10) && shotX<=(iX+100) && InvadersAlive[3][0] && shoot))
						{
							if(shotY>=(iY+140) && shotY<=(iY+250) && InvadersAlive[3][0] && shoot)
							{
								InvadersHealth[3][0]--;
								shoot=false;
								shotDelay=0;
							}
						}
						if(shotX>=(iX+440) && shotX<=(iX+550) && InvadersAlive[3][9] && shoot)
						{
							if(shotY>=(iY+140) && shotY<=(iY+250) && InvadersAlive[3][9] && shoot)
							{
								InvadersHealth[3][9]--;
								shoot=false;
								shotDelay=0;
							}
						}
//shot2
						if((shot2X>=(iX-10) && shot2X<=(iX+100) && InvadersAlive[3][0] && shoot2))
						{
							if(shot2Y>=(iY+140) && shot2Y<=(iY+250) && InvadersAlive[3][0] && shoot2)
							{
								InvadersHealth[3][0]--;
								shoot2=false;
								shot2Delay=0;
							}
						}
						if(shot2X>=(iX+440) && shot2X<=(iX+550) && InvadersAlive[3][9] && shoot2)
						{
							if(shot2Y>=(iY+140) && shot2Y<=(iY+250) && InvadersAlive[3][9] && shoot2)
							{
								InvadersHealth[3][9]--;
								shoot2=false;
								shot2Delay=0;
							}
						}
						if(InvadersAlive[3][0])
						{
							rain1X = iX;
							rain1Y = 250;
							if(XX<=iX+50)
							{
								if(Health>1)
									Health = 1;
								if(!InvadersAlive[3][9])
									Health = 0;
							}
							g.drawImage(Rose, rain1X, rain1Y, this);
						}
						if(InvadersAlive[3][9])
						{
							rain2X = iX + 550;
							rain2Y = 250;
							if(XX+75>=iX+550)
							{
								if(Health>1)
									Health=1;
								if(!InvadersAlive[3][0])
									Health=0;
							}
							g.drawImage(Rose, rain2X, rain2Y, this);
						}
						LC=0;
						RC=0;
						while(LC<=4)
						{
							while(RC<=9)
							{
								if(InvadersHealth[LC][RC]<=0)
								{
									InvadersAlive[LC][RC] = false;
								}
								RC++;
							}
							RC=0;
							LC++;
						}
					}
///////////////Weir
///////////////Lesh
////////////Welnick
					if((InvadersAlive[3][0] || InvadersAlive[3][4] || InvadersAlive[3][9]) && L10R2)
					{
						RippleCount++;
						shotChance=1000;
						int rando=generator.nextInt(shotChance);
						if(BeardCount<2500 && InvadersAlive[3][0])
						{
							BeardCount++;
						}
						if(BeardCount==2500 && InvadersAlive[3][0])
						{
							Beard = true;
						}
						if(Beard)
						{
							g.drawImage(BeardGuard, 0, 350, this);
							g.drawImage(BeardGuard, 250, 350, this);
							g.drawImage(BeardGuard, 500, 350, this);
							g.drawImage(BeardGuard, 750, 350, this);
							g.drawImage(BarberPole, 1100, 0, this);
//							g.drawImage(BeardWarning, 0, 700, this);
						}
						if(rando==0 && ((InvadersAlive[3][0] && !InvadersShot[3][0]) || (InvadersAlive[3][4] && !InvadersShot[3][4]) || (InvadersAlive[3][9] && !InvadersShot[3][0])))
						{
							
							if(InvadersAlive[3][0])
							{
								InvadersShot[3][0] = true;
								InvadersShotX[3][0] = iX;
								InvadersShotY[3][0] = 200;
							}
							if(InvadersAlive[3][4])
							{
								InvadersShot[3][4] = true;
								InvadersShotX[3][4] = iX+200;
								InvadersShotY[3][4] = 200;
							}
							if(InvadersAlive[3][9])
							{
								InvadersShot[3][9] = true;
								InvadersShotX[3][9] = iX+450;
								InvadersShotY[3][9] = 200;
							}
						}
						if(InvadersShot[3][0])
						{
							g.drawImage(L10P2Shot, InvadersShotX[3][0], InvadersShotY[3][0], this);
							InvadersShotY[3][0]++;
							if(InvadersShotY[3][0]>=700)
							{
								InvadersShot[3][0]=false;
							}
							if(InvadersShotX[3][0]<=XX+75 && InvadersShotX[3][0]+75>=XX && InvadersShot[3][0])
							{
								if(InvadersShotY[3][0]+125>=650 && InvadersShotY[3][0]<=700 && InvadersShot[3][0])
								{
									InvadersShot[3][0] = false;
									Health--;
								}
							}
						}
						if(InvadersShot[3][4])
						{
							g.drawImage(L10P2Shot, InvadersShotX[3][4], InvadersShotY[3][4], this);
							InvadersShotY[3][4]++;
							if(InvadersShotY[3][4]>=700)
							{
								InvadersShot[3][4]=false;
							}
							if(InvadersShotX[3][4]<=XX+75 && InvadersShotX[3][4]+75>=XX && InvadersShot[3][4])
							{
								if(InvadersShotY[3][4]+125>=650 && InvadersShotY[3][4]<=700 && InvadersShot[3][4])
								{
									InvadersShot[3][4] = false;
									Health--;
								}
							}
						}
						if(InvadersShot[3][9])
						{
							g.drawImage(L10P2Shot, InvadersShotX[3][9], InvadersShotY[3][9], this);
							InvadersShotY[3][9]++;
							if(InvadersShotY[3][9]>=700)
							{
								InvadersShot[3][9] = false;
							}
							if(InvadersShotX[3][9]<=XX+75 && InvadersShotX[3][9]+75>=XX && InvadersShot[3][9])
							{
								if(InvadersShotY[3][9]+125>=650 && InvadersShotY[3][9]<=700 && InvadersShot[3][9])
								{
									InvadersShot[3][9] = false;
									Health--;
								}
							}
						}
						if(!InvadersAlive[3][0] && !InvadersAlive[3][9])
						{
							rippleFreq = 250;
						}
						if(RippleCount>=rippleFreq && InvadersAlive[3][4])
						{
							RippleCount=1;
							Rippler=true;
							RippleX = iX + 200;
						}
						StunCount++;
						if(Rippler && InvadersAlive[3][4])
						{
							g.drawImage(Ripple, RippleX, 650, this);
							if(XX+75>RippleX && XX<RippleX+100)
							{
								Health--;
								Rippler = false;
								StunCount=0;
							}
						}
						if(Beard)
						{
//shot1
							if(shotX>=1100 && shotX<=1200 && shoot)
							{
								if(shotY>=0 && shotY<=250 && shoot)
								{
									poleHealth--;
									shoot=false;
									shotDelay=0;
								}
							}
//shot2
							if(shot2X>=1100 && shot2X<=1200 && shoot2)
							{
								if(shot2Y>=0 && shot2Y<=250 && shoot2)
								{
									poleHealth--;
									shoot2=false;
									shot2Delay=0;
								}
							}
						}
						if(poleHealth<=0)
						{
							Beard=false;
							BeardCount=0;
							poleHealth=5;
						}
						if(InvadersAlive[3][9])
						{
							g.drawImage(Keyboard, iX-50, 250, this);
							if(shotX>=iX-50 && shotX<=iX+400 && shoot)
							{
								if(shotY>=300 && shotY<=200)
								{
									shoot=false;
									shotDelay=0;
								}
							}
							if(shotX>=iX+450 && shotX<=iX+550 && shoot)
							{
								if(shotY>=150 && shotY<=225)
								{
									shoot = false;
									shotDelay = 0;
									InvadersHealth[3][9] --;
									if(Beard)
										InvadersHealth[3][9] ++;
								}
							}
							if(shot2X>=iX-50 && shot2X<=iX+400 && shoot2)
							{
								if(shot2Y>=300 && shot2Y<=200)
								{
									shoot2=false;
									shot2Delay=0;
								}
							}
							if(shot2X>=iX+450 && shot2X<=iX+550 && shoot2)
							{
								if(shot2Y>=150 && shot2Y<=225)
								{
									shoot2 = false;
									shot2Delay = 0;
									InvadersHealth[3][9] --;
									if(Beard)
										InvadersHealth[3][9] ++;
								}
							}
						}
						if(!InvadersAlive[3][9])
						{
							if(bond)
								g.drawImage(Bond, iX, 200, this);
						}
						{
							if(!bond)
							{
								InvadersHealth[3][0] = 35;
								InvadersHealth[3][4] = 35;
								bond = true;
							}
							if(shotX>=iX && shotX<=iX+100 && shoot==true)
							{
								if(shotY>=150 && shotY<=250)
								{
									if(bond==true)
									{
										shoot=false;
										shotDelay=0;		
										InvadersHealth[3][0]--;
										if(Beard)
										{
											InvadersHealth[3][0] ++;
										}
									}
									if(bond==false)
									{
										InvadersHealth[3][0]--;	
										if(Beard)
										{
											InvadersHealth[3][0] ++;
										}
										shoot=false;
										shotDelay=0;
									}
								}
							}
							if(shot2X>=iX && shot2X<=iX+100 && shoot2==true)
							{
								if(shot2Y>=150 && shot2Y<=250)
								{
									if(bond==true)
									{
										shoot=false;
										shot2Delay=0;		
										InvadersHealth[3][0]--;
										if(Beard)
										{
											InvadersHealth[3][0] ++;
										}
									}
									if(bond==false)
									{
										InvadersHealth[3][0]--;	
										if(Beard)
										{
											InvadersHealth[3][0] ++;
										}
										shoot2=false;
										shot2Delay=0;
									}
								}
							}
							if(shotX>=iX+200 && shotX<=iX+300 && shoot==true)
							{
								if(shotY>=150 && shotY<=250)
								{
									if(bond==true)
									{
										shoot=false;
										shotDelay=0;		
										InvadersHealth[3][0]--;
										if(Beard)
										{
											InvadersHealth[3][0] ++;
										}
									}
									if(bond==false)
									{
										InvadersHealth[3][4]--;	
										if(Beard)
										{
											InvadersHealth[3][4] ++;
										}
										shoot=false;
										shotDelay=0;
									}
								}
							}
							if(shot2X>=iX+200 && shot2X<=iX+300 && shoot2==true)
							{
								if(shot2Y>=150 && shot2Y<=250)
								{
									if(bond==true)
									{
										shoot2=false;
										shot2Delay=0;		
										InvadersHealth[3][0]--;
										if(Beard)
										{
											InvadersHealth[3][0] ++;
										}
									}
									if(bond==false)
									{
										InvadersHealth[3][4]--;	
										if(Beard)
										{
											InvadersHealth[3][4] ++;
										}
										shoot2=false;
										shot2Delay=0;
									}
								}
							}
						}
						//ideas
						//**ALL** drop claws on a 500 random chance.
						/////Welnick wields a keyboard to defend other two teammates while they siege.
						////////Lesh bass amplitude has the ability to kill you if inside the amplitude waves, dropped every 1000 count               ***********************************stun(Stuns deal 1 damage and (with each hit spawn stun pools**250 count stun** with 500 diminishing)) you for 500 count(?) if in radius ***** has 1000 count diminishing returns
						//***Weir and Lesh gain 'Furthur' bond once Welnick is downed             **spirit bond ~both enemies join their health together and share a combinbed health pool**
						////////Weir 
					}
					if(L10R3==true && !InvadersAlive[0][0] && !InvadersAlive[0][3] && !InvadersAlive[0][6] && !InvadersAlive[0][9])
					{
						// freeze game
						//
						// 
						//
						// make an ending screen
						////satisfying ending
						//////maybe a picture of the GD
						//
						// TRY TO GET help on the way PLAYING
						//
					}
					if(L10R3 && (InvadersAlive[0][0] || InvadersAlive[0][3] || InvadersAlive[0][6] || InvadersAlive[0][9]))
					{
						if(InvadersHealth[0][0]<=0)
							InvadersAlive[0][0] = false;
						if(InvadersHealth[0][3]<=0)
							InvadersAlive[0][3] = false;
						if(InvadersHealth[0][6]<=0)
							InvadersAlive[0][6] = false;
						if(InvadersHealth[0][9]<=0)
							InvadersAlive[0][9] = false;
						if(shoot)
						{
							if(shotY>=0 && shotY<=75 && shoot)
							{
								
								if(shotX>=iX && shotX<=iX+100 && shoot && InvadersAlive[0][0])
								{
									InvadersHealth[0][0]--;
									System.out.println("Invader # 1 HIT");
									shoot=false;
									shotDelay=0;
								}
								if(shotX>=iX+150 && shotX<=iX+250 && shoot && InvadersAlive[0][3])
								{
									InvadersHealth[0][3]--;
									System.out.println("Invader # 2 HIT");
									shoot=false;
									shotDelay=0;
								}
								if(shotX>=iX+300 && shotX<=iX+400 && shoot && InvadersAlive[0][6])
								{
									InvadersHealth[0][6]--;
									System.out.println("Invader # 3 HIT");
									shoot=false;
									shotDelay=0;
								}
								if(shotX>=iX+450 && shotX<=iX+550 && shoot && InvadersAlive[0][9])
								{
									InvadersHealth[0][9]--;
									System.out.println("Invader # 4 HIT");
									shoot=false;
									shotDelay=0;
								}
							}
						}
						if(shoot2)
						{
							if(shot2Y>=0 && shot2Y<=75 && shoot2)
							{
								
								if(shot2X>=iX && shot2X<=iX+100 && shoot2 && InvadersAlive[0][0])
								{
									InvadersHealth[0][0]--;
									System.out.println("Invader # 1 HIT");
									shoot2=false;
									shot2Delay=0;
								}
								if(shot2X>=iX+150 && shot2X<=iX+250 && shoot2 && InvadersAlive[0][3])
								{
									InvadersHealth[0][3]--;
									System.out.println("Invader # 2 HIT");
									shoot2=false;
									shot2Delay=0;
								}
								if(shot2X>=iX+300 && shot2X<=iX+400 && shoot2 && InvadersAlive[0][6])
								{
									InvadersHealth[0][6]--;
									System.out.println("Invader # 3 HIT");
									shoot2=false;
									shot2Delay=0;
								}
								if(shot2X>=iX+450 && shot2X<=iX+550 && shoot2 && InvadersAlive[0][9])
								{
									InvadersHealth[0][9]--;
									System.out.println("Invader # 4 HIT");
									shoot2=false;
									shot2Delay=0;
								}
							}
						}
					}
////////////Phase Transitions
					if(((!InvadersAlive[3][0] && !InvadersAlive[3][9]) && (L10R1 || !InvadersAlive[3][4])) || (L10R3 && (InvadersHealth[0][0]<=1 && InvadersHealth[0][3]<=1 && InvadersHealth[0][6]<=1 && InvadersHealth[0][9]<=1)))
					{
						
						
						if(L10R3==true && (InvadersHealth[0][0]<=1 && InvadersHealth[0][3]<=1 && InvadersHealth[0][6]<=1 && InvadersHealth[0][9]<=1))
						{
							L10R3 = false;
//							numDead = 50;
						}
						if(L10R2==true)
						{
							L10R2 = false;
							L10R3 = true;
							InvadersAlive[0][0]=true;
							InvadersAlive[0][3]=true;
							InvadersAlive[0][6]=true;
							InvadersAlive[0][9]=true;
							Invaders[0][0] = getImage(getCodeBase(), "GarciaHead.png");
							Invaders[0][3] = getImage(getCodeBase(), "GarciaHead.png");
							Invaders[0][6] = getImage(getCodeBase(), "GarciaHead.png");
							Invaders[0][9] = getImage(getCodeBase(), "GarciaHead.png");
							InvadersHealth[0][0] = 1;
							InvadersHealth[0][3] = 1;
							InvadersHealth[0][6] = 35;
							InvadersHealth[0][9] = 1;
						}
						if(L10R1==true)
						{
							L10R1 = false;
							L10R2 = true;
							InvadersAlive[3][0] = true;
							InvadersAlive[3][9] = true;
							InvadersAlive[3][4] = true;
							InvadersAlive[4][0] = false;
							InvadersAlive[4][1] = false;
							InvadersAlive[4][2] = false;
							InvadersAlive[4][3] = false;
							InvadersAlive[4][4] = false;
							InvadersAlive[4][5] = false;
							InvadersAlive[4][6] = false;
							InvadersAlive[4][7] = false;
							InvadersAlive[4][8] = false;
							InvadersAlive[4][9] = false;
							InvadersAlive[0][3] = false;
							InvadersAlive[0][0] = false;
							InvadersAlive[0][9] = false;
							Invaders[3][0] = getImage(getCodeBase(), "Weir.png");
							Invaders[3][4] = getImage(getCodeBase(), "Lesh.png");
							Invaders[3][9] = getImage(getCodeBase(), "Welnick.png");
							InvadersHealth[3][9] = 35;
							InvadersHealth[3][4] = 35;
							InvadersHealth[3][0] = 35;
						}
					}
				}
//Ballista
////////ballista collisions and damaging affects
				while(LC<=4)
				{
					while(RC<=9)
					{
						if(shotX>=(iX+(RC*50)-10) && shotX<=(iX+(RC*50)+30) && InvadersAlive[LC][RC]==true && ballista==true)
						{
							if(shotY>=(iY+(LC*50)-10) && shotY<=(iY+(LC*50)+30) && InvadersAlive[LC][RC]==true && ballista==true)
							{
								if(LC==0)
								{
									if(RC==0)
									{
										ballista=false;
										shotDelay=0;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC][RC+1]=false;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC+1][RC+1]=false;
										numDead=numDead+4;
										InvadersHealth[0][0]=InvadersHealth[0][0]-5;
									}
									if(RC==9)
									{
										ballista=false;	
										shotDelay=0;
										InvadersAlive[LC][RC-1]=false;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC+1][RC-1]=false;
										numDead=numDead+4;
									}
									if(RC>0 && RC<9)
									{
										ballista=false;
										shotDelay=0;
										InvadersAlive[LC][RC-1]=false;
										InvadersAlive[LC][RC+1]=false;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC+1][RC+1]=false;
										InvadersAlive[LC+1][RC-1]=false;
										InvadersAlive[LC+1][RC]=false;
										numDead=numDead+6;
									}
								}
								if(LC==4)
								{
									if(RC==0)
									{
										ballista=false;
										shotDelay=0;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC][RC+1]=false;
										InvadersAlive[LC-1][RC]=false;
										InvadersAlive[LC-1][RC+1]=false;
										numDead=numDead+4;

									}
									if(RC==9)
									{
										ballista=false;	
										shotDelay=0;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC][RC-1]=false;
										InvadersAlive[LC-1][RC]=false;
										InvadersAlive[LC-1][RC-1]=false;
										numDead=numDead+4;

									}
									if(RC>0 && RC<9)
									{
										ballista=false;
										shotDelay=0;
										InvadersAlive[LC][RC+1]=false;
										InvadersAlive[LC][RC-1]=false;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC-1][RC+1]=false;
										InvadersAlive[LC-1][RC-1]=false;
										InvadersAlive[LC-1][RC]=false;
										numDead=numDead+6;

									}
								}
								if(LC>0 && LC<4)
								{
									if(RC==0)
									{
										ballista=false;
										shotDelay=0;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC+1][RC+1]=false;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC][RC+1]=false;
										InvadersAlive[LC-1][RC]=false;
										InvadersAlive[LC-1][RC+1]=false;
										numDead=numDead+6;

									}
									if(RC==9)
									{
										ballista=false;	
										shotDelay=0;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC+1][RC-1]=false;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC][RC-1]=false;
										InvadersAlive[LC-1][RC]=false;
										InvadersAlive[LC-1][RC-1]=false;
										numDead=numDead+6;

									}
									if(RC>0 && RC<9)
									{
										ballista=false;
										shotDelay=0;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC+1][RC]=false;
										InvadersAlive[LC][RC+1]=false;
										InvadersAlive[LC][RC-1]=false;
										InvadersAlive[LC][RC]=false;
										InvadersAlive[LC-1][RC+1]=false;
										InvadersAlive[LC-1][RC-1]=false;
										InvadersAlive[LC-1][RC]=false;
										numDead=numDead+9;

									}
								}
							}
						}
						RC++;
					}
					RC=0;
					LC++;
				}
				LC=0;
				RC=0;
				//Level5
				////////Generates Mobs
				////////Moves Mobs
				////////Draws Mobs
/////////start here
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				//ADD SECOND SHOT
				if(level==5 && InvadersHealth[0][0]<30 && InvadersAlive[0][0]==true)
				{
					SpacerCount++;
					if(SpacerCount>=1000)
					{

						Boss1MobsAlive[B1Mobs] = true;
						B1MX[B1Mobs] = iX;
						B1MY[B1Mobs] = iY;
						SpacerCount=0;
						B1Mobs++;
					}
					for(int count=0; count<SpacerCount; count++)
					{
						if(shotX>=B1MX[count]-10 && shotX<=B1MX[count]+30 && shoot==true)
						{
							if(shotY>=B1MY[count]-10 && shotY<=B1MY[count]+30 && shoot==true)
							{
								Boss1MobsAlive[count]=false;
								B1MX[count]=0;
								B1MY[count]=0;
								shoot=false;
								shotDelay=0;
							}
						}
						if(shot2X>=B1MX[count]-10 && shot2X<=B1MX[count]+30 && shoot2==true)
						{
							if(shot2Y>=B1MY[count]-10 && shot2Y<=B1MY[count]+30 && shoot2==true)
							{
								Boss1MobsAlive[count]=false;
								B1MX[count]=0;
								B1MY[count]=0;
								shoot2=false;
								shot2Delay=0;
							}
						}
						if(B1MY[count]>=1000 && Boss1MobsAlive[count]==true)
							Health=0;
						if(Boss1MobsAlive[count]==true)
							g.drawImage(Invaders[4][4], B1MX[count], B1MY[count], this);
						B1MY[count]++;
					}
					if(shotX>=iX+150 && shotX<=iX+250 && shoot==true)
					{
						if(shotY>=iY && shotY<=iY+100 && shoot==true)
						{
							InvadersHealth[0][0]--;
							System.out.println("Boss Hit! HULL INTEGRITY REMAINING: "+InvadersHealth[0][0]);
							shoot=false;
							shotDelay=0;
						}
					}
					if(shot2X>=iX+150 && shot2X<=iX+250 && shoot2==true)
					{
						if(shot2Y>=iY && shot2Y<=iY+100 && shoot2==true)
						{
							InvadersHealth[0][0]--;
							System.out.println("Boss Hit! HULL INTEGRITY REMAINING: "+InvadersHealth[0][0]);
							shoot2=false;
							shot2Delay=0;
						}
					}
					if(InvadersHealth[0][0]<=0)
					{
						InvadersAlive[0][0]=false;
						numDead=50;
					}
				}
				//Level 5
				////////Determines Strafe hole;
				if(level==5 && InvadersHealth[0][0]<45 && InvadersHealth[0][0]>=30 && StrafeActive==false)
				{
					for(int count=0; count<50; count++)
					{
						StrafeHole[count]=false;
					}
					rando = generator.nextInt(40);
					for(int count=0; count<8; count++)
					{
						StrafeHole[(rando+count)] = true;
					}
					StrafeActive=true;
				}
				//Level 5
				////////Draws Strafe
				////////Strafe collisions
				if(StrafeActive==true)
				{
					for(int count=0; count<50; count++)
					{
						if(StrafeHole[count]==false)
							g.drawImage(eShot, Strafe[count], StrafeY, this);
						if(Strafe[count]>=XX && Strafe[count]<=XX+75 && StrafeHole[count]!=true)
						{
							if(StrafeY>650 && StrafeY<700)
							{
								System.out.println("Operation: Flak Fire Successful \n"
										+ " Enemy Eliminated \n"
										+ " Damage Report: Right Wing     - Wing skin destroyed **FULLY INCAPACITATED\n"
										+ "					Left Wing      - Minor damage sustained \n"
										+ "                 Hull Integrity - Fully Functional");
								Health=0;
							}
						}
					}
					StrafeY++;
				}
				//X position of Flak
				if(numOfFlak>0 && InvadersHealth[0][0]>45)
				{
					for(int count=0; count<numOfFlak; count++)
					{
						System.out.println(flakX[count]);
						System.out.println(XX);
						if((flakX[count])<=XX+75 && (flakX[count]+225)>=XX && flakCount[count]>1000)
						{
							System.out.println("Operation: Flak Fire Successful \n"
									+ " Enemy Eliminated \n"
									+ " Damage Report: Right Wing      - minor sustained damage \n"
									+ "        	Left Wing      - Fully Functional \n"
									+ "                Hull Integrity - Fully Functional");
							Health=0;
						}
					}
				}
				//Ends Strafe
				if(StrafeY>900)
				{
					StrafeActive=false;
					StrafeY=0;
				}
				//Level 5 
				////////~Draw Flak
				////////~Flak Location
				////////~Boss Left Wing
				////////~Boss Right Wing
				//Level 1-4
				////////~killing enemies
				////////~damaging enemies
				////////~ends game if enemies get too low
				while(LC<=4)
				{
					while(RC<=9)
					{
						if(level==5)
						{
							if(InvadersHealth[0][0]>45 && numOfFlak>0)
							{
								for(int counttt=0; counttt<numOfFlak; counttt++)
								{
									if(flakCount[counttt]<=1000)
										g.drawImage(warning, flakX[counttt], 600, this);
									else
										g.drawImage(Flak, flakX[counttt], 600, this);
									flakCount[counttt]++;
								}
							}
							if(numOfFlak>0)
							{
								if(flak[(numOfFlak-1)]==false && flakX[(numOfFlak-1)]==0)
								{
									flakX[(numOfFlak-1)]=shotX;
								}
							}
							if(shotY>=iY && shotY<=(iY+75))
							{
								if(shotX>=(iX) && shotX<=(iX+135) && InvadersHealth[0][0]>=45 && shoot==true)
								{
									shoot=false;	
									shotDelay=0;
									flak[numOfFlak]=false;
									numOfFlak++;
									InvadersHealth[0][0]--;
									System.out.println("Boss Hit! "+InvadersHealth[0][0]+" health remaining left wing "+(InvadersHealth[0][0]-45));
								}
								if(shotX>=(iX+295) && shotX<=(iX+450) && InvadersHealth[0][0]<=45 && InvadersHealth[0][0]>=30 && shoot==true)
								{
									shoot=false;
									shotDelay=0;
									InvadersHealth[0][0]--;
									System.out.println("Boss Hit! "+InvadersHealth[0][0]+" health remaining right wing "+(InvadersHealth[0][0]-30));
								}
							}
							if(shot2Y>=iY && shot2Y<=(iY+75))
							{
								if(shot2X>=(iX) && shot2X<=(iX+135) && InvadersHealth[0][0]>=45 && shoot2==true)
								{
									shoot2=false;	
									shot2Delay=0;
									flak[numOfFlak]=false;
									numOfFlak++;
									InvadersHealth[0][0]--;
									System.out.println("Boss Hit! "+InvadersHealth[0][0]+" health remaining left wing "+(InvadersHealth[0][0]-45));
								}
								if(shot2X>=(iX+295) && shot2X<=(iX+450) && InvadersHealth[0][0]<=45 && InvadersHealth[0][0]>=30 && shoot2==true)
								{
									shoot2=false;
									shot2Delay=0;
									InvadersHealth[0][0]--;
									System.out.println("Boss Hit! "+InvadersHealth[0][0]+" health remaining right wing "+(InvadersHealth[0][0]-30));
								}
							}

						}
						if(shotX>=(iX+(RC*50)-10) && shotX<=(iX+(RC*50)+30) && InvadersAlive[LC][RC]==true && shoot==true && level!=5)
						{
							if(shotY>=(iY+(LC*50)-10) && shotY<=(iY+(LC*50)+30) && InvadersAlive[LC][RC]==true && shoot==true)
							{
								InvadersHealth[LC][RC]--;
								if(level==10 && (InvadersHealth[LC][RC]>5))
									InvadersHealth[LC][RC]++;
								if(InvadersHealth[LC][RC]<=0)
								{
									InvadersAlive[LC][RC]=false;
									numDead++;
									scoore=scoore+5;
									System.out.println("Score: "+scoore+" Enemy killed  **+5**");
								}
								scoore++;
								System.out.println("Score: "+scoore+" Enemy Hit     **+1**");
								shoot=false;
								shotDelay=0;
							}
						}
						if(shot2X>=(iX+(RC*50)-10) && shot2X<=(iX+(RC*50)+30) && InvadersAlive[LC][RC]==true && shoot2==true && level!=5)
						{
							if(shot2Y>=(iY+(LC*50)-10) && shot2Y<=(iY+(LC*50)+30) && InvadersAlive[LC][RC]==true && shoot2==true)
							{
								InvadersHealth[LC][RC]--;
								if(level==10 && (InvadersHealth[LC][RC]>5))
									InvadersHealth[LC][RC]++;
								if(InvadersHealth[LC][RC]<=0)
								{
									InvadersAlive[LC][RC]=false;
									numDead++;
									scoore=scoore+5;
									System.out.println("Score: "+scoore+" Enemy killed  **+5**");
								}
								scoore++;
								System.out.println("Score: "+scoore+" Enemy Hit     **+1**");
								shoot2=false;
								shot2Delay=0;
							}
						}
						if(InvadersAlive[LC][RC]==true)
						{
							g.drawImage(Invaders[LC][RC], iX+(RC*50), iY+(LC*50), this);
							if((iY+(LC*50))>=750)
								Health=0;
						}
						RC++;
					}
					RC=0;
					LC++;
				}
				if(numDead>=50)
				{
					Health=5;
					LC=0;
					RC=0;
					level++;
					numDead=0;
					ballistaRemaining=1;
					while(LC<=4)
					{
						while(RC<=9)
						{
							if(level!=5 && level!=10)
								InvadersAlive[LC][RC] = true;
							if(level==2)
							{
								Invaders[LC][RC] = getImage(getCodeBase(), "RedBear.png");
								InvadersHealth[LC][RC] = 2;
							}
							if(level==3)
							{
								Invaders[LC][RC] = getImage(getCodeBase(), "YellowBear.png");
								int random=generator.nextInt(2);
								if(random==0)
								{
									numDead++;
									InvadersHealth[LC][RC] = 0;
									InvadersAlive[LC][RC] = false;
								}
							}
							if(level==4)
							{
								int randum=generator.nextInt(3);
								if(randum==0)
								{
									Invaders[LC][RC] = getImage(getCodeBase(), "RedBear.png");
									InvadersHealth[LC][RC] = 2;
								}
								if(randum==1)
								{
									Invaders[LC][RC] = getImage(getCodeBase(), "YellowBear.png");
									InvadersHealth[LC][RC] = 1;
								}
								if(randum==2)
								{
									Invaders[LC][RC] = getImage(getCodeBase(), "GreenBear.png");
									InvadersHealth[LC][RC] = 1;
								}
							}
							if(level==5)
							{
								BKG = getImage(getCodeBase(), "GarciaBKG.png");
								InvadersAlive[0][0] = true;
								InvadersHealth[0][0] = 50;
								Invaders[0][0] = getImage(getCodeBase(), " GDIboss1.png");
							}
							if(level==6)
							{
								InvadersAlive[LC][RC] = false;
								InvadersHealth[LC][RC] = 0;
								level=10;
							}
							if(level==10)
							{
								L10R1 = true;
								BKG = getImage(getCodeBase(), "Tye-DyeBearsBKG.png");
								//Invaders
								InvadersAlive[4][RC] = true;
								InvadersHealth[4][RC] = 1;
								InvadersAlive[4][0] = false;
								InvadersAlive[4][1] = false;
								InvadersAlive[4][9] = false;
								//									InvadersAlive[2][RC] = true;
								//									InvadersHealth[2][RC] = 1;
								//									InvadersAlive[0][RC] = true;
								//									InvadersHealth[0][RC] = 1;
								//Garcia
								InvadersAlive[0][6] = true;
								InvadersHealth[0][6] = 35;
								Invaders[0][6] = getImage(getCodeBase(), "GarciaHead.png");
								//Weir
								InvadersAlive[0][9] = true;
								InvadersHealth[0][9] = 35;
								Invaders[0][9] = getImage(getCodeBase(), "Weir.png");
								//Lesh
								InvadersAlive[0][0] = true;
								InvadersHealth[0][0] = 35;
								Invaders[0][0] = getImage(getCodeBase(), "Lesh.png");
								//Welnick
								InvadersAlive[0][3] = true;
								InvadersHealth[0][3] = 35;
								Invaders[0][3] = getImage(getCodeBase(), "Welnick.png");
								//kreutzmann
								InvadersAlive[3][9] = true;
								InvadersHealth[3][9] = 35;
								Invaders[3][9] = getImage(getCodeBase(), "Kreutzmann.png");
								//hart
								InvadersAlive[3][0] = true;
								InvadersHealth[3][0] = 35;
								Invaders[3][0] = getImage(getCodeBase(), "Hart.png");
								//Player position
								XX=300;

							}
							RC++;
						}
						RC=0;
						LC++;
					}
					iY=0;
					iX=0;
					InvDir=true;
				}
				g.drawImage(Wilmer, XX, 650, this);
				RC=0;
				LC=0;
				while(LC<=4)
				{
					while(RC<=9)
					{
						int rando=generator.nextInt(shotChance);
						if(rando==0 && InvadersShot[LC][RC]==false && InvadersAlive[LC][RC]==true)
						{
							InvadersShot[LC][RC] = true;
							InvadersShotX[LC][RC] = iX+(RC*50);
							InvadersShotY[LC][RC] = iY+(LC*50);
						}
						RC++;
					}
					LC++;
					RC=0;
				}
				RC=0;
				LC=0;
				if(level==1)
				{
					if(numDead==49)
					{
						shotSpeed=8;
						shotChance=50;
						BrokenArrow=true;
					}
					if(numDead<49)
					{
						shotChance=5000;
						shotSpeed=4;
						BrokenArrow=false;
					}	
				}
				if(level==2)
				{
					if(numDead==49)
					{
						shotSpeed=10;
						shotChance=50;
						BrokenArrow=true;
					}
					if(numDead<49)
					{
						shotChance=5000;
						shotSpeed=4;
						BrokenArrow=false;
					}		
				}
				if(level==3)
				{
					if(numDead==49)
					{
						shotSpeed=3;
						shotChance=1;
						BrokenArrow=true;
					}
					if(numDead<49)
					{
						shotChance=250;
						shotSpeed=2;
						BrokenArrow=false;
					}	
				}
				if(level==4)
				{
					shotSpeed=4;
					shotChance=2500;
					BrokenArrow=false;
				}
				if(level==5)
				{
					shotChance = 500;
					shotSpeed = 6;
				}
				LC=0;
				RC=0;
				while(LC<=4)
				{
					while(RC<=9)
					{
						if(InvadersShot[LC][RC]==true)
						{
							InvadersShotY[LC][RC] = InvadersShotY[LC][RC] + shotSpeed;
							g.drawImage(eShot, InvadersShotX[LC][RC], InvadersShotY[LC][RC], this);
							if(InvadersShotY[LC][RC]>=1000)
								InvadersShot[LC][RC] = false;
							if((InvadersShotX[LC][RC]>=XX) && (InvadersShotX[LC][RC]<=XX+70) && (InvadersShot[LC][RC]==true))
							{
								if((InvadersShotY[LC][RC]>=650) && (InvadersShotY[LC][RC]<=700) && (InvadersShot[LC][RC]==true))
								{
									Health--;
									System.out.println("hit by ship in row "+(LC+1)+" column "+(RC+1));
									System.out.println("Health: "+Health);
									InvadersShot[LC][RC] = false;
								}
							}
						}
						RC++;
					}
					LC++;
					RC=0;
				}
				int rando=generator.nextInt(3);
				if(level!=5 && level!=10)
				{
					if(iX>=500 && InvDir==true)
					{
						InvDir=false;
						iY=iY+100;
					}
					if(iX<=0 && InvDir==false)
					{
						InvDir=true;
						iY=iY+100;
					}
					if(InvDir==true)
					{
						if(rando==2)
							iX++;
					}
					else
					{
						if(rando==2)
							iX--;
					}
					if(BrokenArrow==true)
					{
						if(rando==2)
						{
							iY++;
						}
					}
				}
				if(level==5 || level==10)
				{
					if(iX<=0)
					{
						InvDir=true;
						if(level==10 && L10R3)
						{
							System.out.println("went through");
							int Garcia = InvadersHealth[0][0];
							InvadersAlive[0][0] = true;
							InvadersAlive[0][3] = true;
							InvadersAlive[0][6] = true;
							InvadersAlive[0][9] = true;
							InvadersHealth[0][0] = InvadersHealth[0][3];
							if(InvadersHealth[0][0]<1)
								InvadersHealth[0][0] = 1;
							InvadersHealth[0][3] = InvadersHealth[0][6];
							if(InvadersHealth[0][3]<1)
								InvadersHealth[0][3] = 1;
							InvadersHealth[0][6] = InvadersHealth[0][9];
							if(InvadersHealth[0][6]<1)
								InvadersHealth[0][6] = 1;
							InvadersHealth[0][9] = Garcia;
							if(InvadersHealth[0][9]<1)
								InvadersHealth[0][9] = 1;
							System.out.println(InvadersHealth[0][0]);
							System.out.println(InvadersHealth[0][3]);
							System.out.println(InvadersHealth[0][6]);
							System.out.println(InvadersHealth[0][9]);
							//********ROTATION OF WHERE GARCIA IS HIDING
							//********REMEMBER! PUT IN THE ABILITY TO SHOOT MORE RAPIDLY AS THE HEADS ARE KILLED IN EACH PATH **DIRS INVDIRS**
						}
					}
					else if(iX>750 && level==5)
						InvDir=false;
					else if(iX>650 && level==10)
					{
						InvDir = false;
						if(level==10 && L10R3)
						{
							System.out.println("went through");
							int Garcia = InvadersHealth[0][0];
							InvadersAlive[0][0] = true;
							InvadersAlive[0][3] = true;
							InvadersAlive[0][6] = true;
							InvadersAlive[0][9] = true;
							InvadersHealth[0][0] = InvadersHealth[0][3];
							if(InvadersHealth[0][0]<1)
								InvadersHealth[0][0] = 1;
							InvadersHealth[0][3] = InvadersHealth[0][6];
							if(InvadersHealth[0][3]<1)
								InvadersHealth[0][3] = 1;
							InvadersHealth[0][6] = InvadersHealth[0][9];
							if(InvadersHealth[0][6]<1)
								InvadersHealth[0][6] = 1;
							InvadersHealth[0][9] = Garcia;
							if(InvadersHealth[0][9]<1)
								InvadersHealth[0][9] = 1;
							System.out.println(InvadersHealth[0][0]);
							System.out.println(InvadersHealth[0][3]);
							System.out.println(InvadersHealth[0][6]);
							System.out.println(InvadersHealth[0][9]);
							//********ROTATION OF WHERE GARCIA IS HIDING
							//********REMEMBER! PUT IN THE ABILITY TO SHOOT MORE RAPIDLY AS THE HEADS ARE KILLED IN EACH PATH **DIRS INVDIRS**
						}
					}
					if(InvDir==true && iX<=750)
						iX++;
					if(InvDir==false && iX>0)
						iX--;
				}
				if(GameRunning==false)
				{
					//					if(violenceL==false && hatredL==false && doubtL==false && despairL==false && fearL==false && angerL==false)
					//					{
					//						offscr.setColor(getBackground());
					//						offscr.fillRect(0, 0, width, height);
					//						offscr.setColor(Color.BLACK);
					//						offscr.drawString("Game by: Zackary Zaleski", 5, 350);
					//						offscr.drawString("If you enjoyed this game, please continue to support Zack Zaleski and all of his endeavors", 5, 400);
					//						offscr.drawString("Re-run the program to play again", 0, 0);
					//						offscr.drawString("Game over", 300, 300);
					//						offscr.drawRect(300, 310, 80, 20);
					//						offscr.drawString("YOU WIN!!", 303, 323);
					//						offscr.setColor(Color.BLACK);
					////						offscr.drawRect(0, 0, 499, 499);
					//						g.drawImage(offscreenImage, 0, 0, this);
					//						if(savedscore==0)
					//						{
					//							savedscore=1;
					//							saver=0;
					//						}
					//						this.start();
					//					}
					//					else
					//					{
					//						offscr.setColor(getBackground());
					//						offscr.fillRect(0, 0, 2000, 1000);
					//						offscr.setColor(Color.BLACK);
					//						offscr.drawString("Game by: Zackary Zaleski", 5, 350);
					//						offscr.drawString("If you enjoyed this game, please continue to support Zack Zaleski and all of his endeavors", 5, 400);
					//						offscr.drawString("scoore: "+scoore, 30, 50);
					//						offscr.drawString("Re-run the program to play again", 30, 100);
					//						offscr.drawString("Game over", 300, 300);
					//						offscr.drawRect(300, 310, 80, 20);
					//						offscr.drawString("YOU SUCK!", 303, 323);
					//						offscr.setColor(Color.BLACK);
					////						offscr.drawRect(0, 0, 499, 499);
					//						g.drawImage(offscreenImage, 0, 0, this);
					//						if(savedscore==0)
					//						{
					//							savedscore=1;
					//							saver=0;
					//						}
					//						this.start();
					//					}

				}
			}
			else
			{
				//							offscr.drawString("Out of Bounds", 250, 250);
				//							g.drawImage(offscreenImage, 0, 0, this);
				GameRunning=false;
				//				offscr.setColor(getBackground());
				//				offscr.fillRect(0, 0, width, height);
				//				offscr.setColor(Color.BLACK);
				//				offscr.setColor(Color.RED);
				//				offscr.drawString("Game paused!", 300, 285);
				//				offscr.drawString("Click to continue", 300, 300);
				//				offscr.drawRect(300, 310, 80, 20);
				//				offscr.drawString("Play!", 303, 323);
				//				offscr.setColor(Color.BLACK);
				//				offscr.drawRect(0, 0, 2000, 1000);
				//				g.drawImage(offscreenImage, 0, 0, this);
				//				g.drawImage(Shot, 0, 0, this);
			}
		}

		else
		{
			// Game has not started yet.
			g.drawImage(Title, 0, 0, this);
			//g.drawString("X: "+xpos, 350, 490);
			//g.drawString("Y: "+ypos, 425, 490);
		}
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void run()
	{
		while(PlayAgain==true)
		{
			while(GameRunning==true)
			{
				//				SpeedCheck();
				//				SpeedCheckMin();
				MouseHit();
				repaint();
				try
				{
					Thread.sleep(2);
				}
				catch (InterruptedException e){};
			}
			if(GameRunning==false)
			{
				//				xpos=0;ypos=0;
				//				xcen=0;ycen=0;
				//				xsqu=0;ysqu=0;
				//				xcircle=0; yplus=0;yminus=0; addvalue=0;
				//				vplus=0;
				//				counter=0;
				//				for(int x=0;x<8;x++)
				//				{
				//					for(int y=0;y<25;y++)
				//						randomcircles[x][y]=0;
				//				}
				//				circles=false;
				//				touched=false;
			}
		}	
	}
	public void MouseHit()
	{
		//		for(int x=0;x<counter;x++)
		//		{
		//			if(d.CircleCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x],xcen, ycen, 10))
		//			{
		//				savedscore=score;
		//				GameRunning=false;
		//			}
		//		}
	}
	public void SpeedCheck()
	{
		//		for(int x=0;x<counter;x++)
		//		{
		//			if(Math.abs(randomcircles[3][x])>vmax)
		//				randomcircles[3][x]*=.5;
		//			if(Math.abs(randomcircles[4][x])>vmax)
		//				randomcircles[4][x]*=.5;
		//		}
	}
	public void SpeedCheckMin()
	{
		//		for(int x=0;x<counter;x++)
		//		{
		////			if(Math.abs(randomcircles[3][x])<vmin)
		////				randomcircles[3][x]*=2.5;
		////			if(Math.abs(randomcircles[4][x])<vmin)
		////				randomcircles[4][x]*=2.5;
		//		}
	}
	public int PositiveNegative(double number)
	{
		if(number>=0)
			return 1;
		else
			return -1;
	}
	public boolean PositionCheck(int x)
	{
		//		if(d.CircleSpawnCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x], xcen, ycen, 10)==true)
		//		{
		//			//randomcircles[0][x]+=150;
		//			//randomcircles[1][x]+=150;
		//			return true;
		//		}	
		//		return false;
		return true;
	}
	public boolean BallSpawn(int x)
	{
		//		for( int y=0;y<counter;y++)
		//		{
		//			if(d.CircleCollision(randomcircles[6][x], randomcircles[7][x], randomcircles[5][x], randomcircles[6][y], randomcircles[7][y], randomcircles[5][y])==true)
		//			{
		//				//randomcircles[0][x]=r.nextInt(200+100);
		//				//randomcircles[1][x]=r.nextInt(200+100);
		//				return true;
		//			}
		//			return false;
		//		}
		return false;
	}
	public void mouseDragged(MouseEvent ev) {
		// TODO Auto-generated method stub
		//		xpos=ev.getX()-10;
		//		ypos=ev.getY()-10;
		//		xcen=ev.getX();
		//		ycen=ev.getY();
		//repaint();
	}
	public void mouseMoved(MouseEvent ev) {
		// TODO Auto-generated method stub
		//		xpos=ev.getX()-10;
		//		ypos=ev.getY()-10;
		//		xcen=ev.getX();
		//		ycen=ev.getY();

		//repaint();
	}
	public void mouseClicked(MouseEvent ev)
	{
		// TODO Auto-generated method stub
		//		dweller.play();
		if (!hasStarted)
		{
			if ((ev.getX() >= 240 && ev.getX() <= 290) && (ev.getY() >= 310 && ev.getY() <= 350))
			{
				playNormal=true;
				savedscore=0;
			}
			else if ((ev.getX() >= 385 && ev.getX() <= 435) && (ev.getY() >= 310 && ev.getY() <= 350))
				playSeeker=true;

			hasStarted=true;
		}
		if(GameRunning==false)
		{
			if(ev.getX()>0&&ev.getX()<1000&&ev.getY()>0&&ev.getY()<1000)
			{
				GameRunning=true;
				//System.out.println("clicked");
			}
		}		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		onScreen=true;
		inBounds=true;
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent ev)
	{
		onScreen=false;
		inBounds=false;
		// TODO Auto-generated method stub
		//GameRunning=false;
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//		dweller.play();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//		dweller.play();

	}
	@Override
	public void keyPressed(KeyEvent e)
	{
		int key=e.getKeyCode();
		if(key==e.VK_A)
			left=true;
		if(key==e.VK_D)
			right=true;
		if(key==e.VK_Q && shotDelay==0 && shoot==false && ballista==true)
		{
			shoot=true;
		}
		if(key==e.VK_E && shot2Delay==0 && !shoot2)
		{
			shoot2=true;
		}
//		if(key==e.VK_F && shotDelay==0 && shoot==false && ballistaRemaining>0)
//		{
//			ballista=true;
//			ballistaRemaining--;
//		}
		
	}
	@Override
	public void keyReleased(KeyEvent e)
	{
		int key=e.getKeyCode();
		if(key==e.VK_A)
			left=false;
		if(key==e.VK_D)
			right=false;
		if(key==e.VK_Q && shotDelay==0)
			shoot=true;
		if(key==e.VK_E && shot2Delay==0 && !shoot2)
		{
			shoot2=true;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) 
	{


	}
	public void playSound(String location)
	{
		try {
			AudioClip clip = Applet.newAudioClip(
			new URL(location));
			clip.play();
			} catch (MalformedURLException murle) {
			System.out.println(murle);
			}
	}
	public void loopSound(String location)
	{
		try {
			AudioClip clip = Applet.newAudioClip(
			new URL(location));
			clip.loop();
			} catch (MalformedURLException murle) {
			System.out.println(murle);
			}
	}

}