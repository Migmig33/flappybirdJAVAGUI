/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.flappybird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
/**
 *
 * @author migzt
 */
public class context1 extends JPanel implements ActionListener, KeyListener{
     int boardWidth = 360;
     int boardHeight = 640;
    
     //Images
     Image backgroundImg;
     Image birdImgs;
     Image botpipes;
     Image toppipes;
     
     //Bird
     int birdX = boardWidth/8;
     int birdY = boardHeight/2;
     int birdW = 34;
     int birdH = 24;
     
    
    
     
     class Bird {
         int x = birdX;
         int y = birdY;
         int w = birdW;
         int h = birdH;
         Image img;
         
         Bird(Image img){
             this.img = img;
         
         }
     
     
     
   
    
     
     }
     
    //Pipes
    int pipeX = boardWidth;
    int pipeY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    class Pipe{
        int x = pipeX;
        int y = pipeY;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;
        Pipe(Image img) {
            this.img = img;
        
        }
    
    }
       
    //logic
    Bird bird;
    int vX = -4; // moves pipe to the left
    int vY = 0; //move bird up/down speed
    int gravity = 1;
    
    ArrayList<Pipe> pipes;
    Random random = new Random();
    Timer gameLoop;
    Timer placePipesTimer;
    
    boolean gameOver = false;
    double score = 0;

    
    context1() {
          
         setPreferredSize(new Dimension(boardWidth, boardHeight));
         setFocusable(true);
         addKeyListener(this);
         //setBackground(Color.blue);
         
         backgroundImg = new ImageIcon(getClass().getResource("/bgImg.png")).getImage();
         birdImgs = new ImageIcon(getClass().getResource("/birdImg.png")).getImage();
         botpipes = new ImageIcon(getClass().getResource("/botpipe.png")).getImage();
         toppipes = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
         
         //bird
         bird = new Bird(birdImgs);
         pipes = new ArrayList<Pipe>();
         //place pipes timer
         placePipesTimer = new Timer(1500, new ActionListener(){
             @Override
             public void actionPerformed(ActionEvent e){
                 placePipes();
             
             }
         
         });
         placePipesTimer.start();
     
         //gametimer
         gameLoop = new Timer(1000/60, this);
         gameLoop.start();
          
         
   
         
     
                             
    }
    public void placePipes() {
        //(0-1) * pipeHieght/2 -> 0-256
        //128
        //0 - 28 - (0-256) --> pipeHeight4 -> 3/4 pipeHeight
        int randomPipeY = (int) (pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;
        
        Pipe topPipe = new Pipe(toppipes);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);
        
        Pipe bottomPipe = new Pipe(botpipes);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
        
        
    
    
    }
  
     public void paintComponent(Graphics g) {
         super.paintComponent(g);
         draw(g);
     
     
     }
   
     public void draw(Graphics g) {
         //System.out.println("aaso");
         //bg   
         g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null); 
         //bird
         g.drawImage(bird.img, bird.x, bird.y, bird.w, bird.h, null);
         //pipes
         for (int i = 0; i < pipes.size(); i++){
             Pipe pipe = pipes.get(i);
             g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
         
         }
         
         //score
         g.setColor(Color.yellow);
         g.setFont(new Font("Times New Roman", Font.PLAIN, 32));
         if (gameOver){
             g.drawString("GameOver: " + String.valueOf((int) score), 10, 35);
             g.drawString("Press 'Space' to Try Again", 10, 70);

         }
         else{
             g.drawString(String.valueOf((int) score), 10, 35);
         }
                 
     
          
     
     }
     public void move(){
         //bird
         vY += gravity;
         bird.y += vY;
         bird.y = Math.max(bird.y, 0);
         
         //pipes
         for (int i = 0; i < pipes.size(); i++){
             Pipe pipe = pipes.get(i);
             pipe.x += vX;
             
             
             if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                 pipe.passed = true;
                 score += 0.5;
             }
             if (collision(bird, pipe)) {
                 gameOver = true;
             
             }
         }
         
         if (bird.y > boardHeight) {
             gameOver = true;
         }
        
       
     
     }
     @Override
     public void actionPerformed(ActionEvent e){
         move();
         repaint();
         if (gameOver) {
             placePipesTimer.stop();
             gameLoop.stop();
         }
     
     }
     public boolean collision(Bird a, Pipe b) {
         return a.x < b.x + b.width &&
                a.x + a.w > b.x &&
                a.y < b.y + b.height &&
                a.y + a.h > b.y;
     
     };
     @Override
     public void keyPressed(KeyEvent e){
         if (e.getKeyCode() == KeyEvent.VK_SPACE){
             vY = -9;
             if (gameOver) {
                 //reset the games
                 bird.y = birdY;
                 vY = 0;
                 pipes.clear();
                 score = 0;
                 gameOver = false;
                 gameLoop.start();
                 placePipesTimer.start();
             }
         
         }   
     }
     @Override
     public void keyReleased(KeyEvent e) {
    // No action needed for now
    }

     @Override
     public void keyTyped(KeyEvent e) {
     // No action needed for now
     }
 
     
     
     
     
  
}
    




    
 
     
 
    
     
 
   