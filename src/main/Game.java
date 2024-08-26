package main;

public class Game implements Runnable{
	
	private GameWindow gameWindow;
	private GamePanel  gamePanel;
	private Thread gameThread;
	private final int FPS_SET=120;
	private final int UPS_SET=200;
	
	public Game(){
		gamePanel=new GamePanel();
		gameWindow=new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
		
	}
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		gamePanel.updateGame();
	}
	 
	@Override
	public void run() {
		double timePerFrame = 1000000000.0/FPS_SET;
		double timePerUpdate = 1000000000.0/UPS_SET;
		
		long previousTime=System.nanoTime();
		
		int frames=0;
		int update=0;
		long lastChecked=System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;
		
		while(true) {
			long currentTime=System.nanoTime();
			
			deltaU += (currentTime - previousTime)/timePerUpdate;
			deltaF += (currentTime - previousTime)/timePerFrame;
			
			previousTime=currentTime;
			if(deltaU>=1.0) {
				update();
				update++;
			   	deltaU--;
			}
			
			if(deltaF>=1.0) {
				gamePanel.repaint();
				frames++; 
				deltaF--;
			}
			
			if(System.currentTimeMillis()-lastChecked>=1000) {
				lastChecked=System.currentTimeMillis();
				System.out.println("FPS:"+frames+ "| UPS:"+update);
				frames=0;
				update=0;
			}
		}
	}
}
