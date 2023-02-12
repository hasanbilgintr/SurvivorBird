package com.hasanbilgin.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

import sun.rmi.runtime.Log;

public class SurvivorBird extends ApplicationAdapter {
    //oyunumuzdaki herşey resim arkaplan resmi vs herşey sprite diyoruz
    SpriteBatch batch;
    //imaje /resim yaratmak için sınıf
    Texture backgraund;
    Texture bird;
    Texture bee1;
    Texture bee2;
    Texture bee3;
    float birdX = 0;
    float birdY = 0;
    int gameState = 0;
    //hız
    float velocity = 0;
    //hız ivmesi
    float gravity = 0.1f;
    float enemyVelocity = 2;
    Random random;
    int score = 0;
    int scoreEnemy = 0;
    //yazdırmak için
    BitmapFont font;
    BitmapFont font2;

    //kuş etrafı kenarlık çizmek için
    //badlogic
    Circle birdCircle;
    //şekil renklendirmek için
    ShapeRenderer shapeRenderer;

    int numberOfEnemies = 4;
    //düşman
    float[] enemyX = new float[numberOfEnemies];
    float[] enemyOffSet1 = new float[numberOfEnemies];
    float[] enemyOffSet2 = new float[numberOfEnemies];
    float[] enemyOffSet3 = new float[numberOfEnemies];
    float distance = 0;

    Circle[] enemyCircles1;
    Circle[] enemyCircles2;
    Circle[] enemyCircles3;


    @Override
    //ilk okunan metot// oyun açıldığında vs..
    public void create() {
        batch = new SpriteBatch();
        //imaje tanımladık
        backgraund = new Texture("backgraund.png");
        bird = new Texture("bird.png");
        bee1 = new Texture("bee.png");
        bee2 = new Texture("bee.png");
        bee3 = new Texture("bee.png");

        distance = Gdx.graphics.getWidth() / 2;
        random = new Random();

        birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
        birdY = Gdx.graphics.getHeight() / 3;

        shapeRenderer = new ShapeRenderer();

        birdCircle = new Circle();
        enemyCircles1 = new Circle[numberOfEnemies];
        enemyCircles2 = new Circle[numberOfEnemies];
        enemyCircles3 = new Circle[numberOfEnemies];

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(4);

        font2 = new BitmapFont();
        font2.setColor(Color.WHITE);
        font2.getData().setScale(4);

        for (int i = 0; i < numberOfEnemies; i++) {
            enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
            enemyX[i] = Gdx.graphics.getWidth() - bee1.getHeight() / 2 + i * distance;


            enemyCircles1[i] = new Circle();
            enemyCircles2[i] = new Circle();
            enemyCircles3[i] = new Circle();
        }

    }

    @Override
    //oyun devam ettiği sürece devamlı çağırılan metot
    public void render() {
        //batch başlatmak
        batch.begin();
        //resmi çizmek
        //2.parametre x konumu 3.p y konumu ,4.p genişlik,5p yükseklik
        batch.draw(backgraund, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //ekrana dokunulduğunda kastediliyor
        //Gdx.input.isTouched()
        if (gameState == 1) {

            if (enemyX[scoreEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
                score++;

                if (scoreEnemy < numberOfEnemies - 1) {
                    scoreEnemy++;
                } else {
                    scoreEnemy = 0;
                }
            }


            if (Gdx.input.isTouched()) {
                //velocity=-bird.getHeight()/200;
                //velocity=-Gdx.graphics.getHeight()/(bird.getHeight()/5);
                velocity = -7;
            }

            for (int i = 0; i < numberOfEnemies; i++) {
                if (enemyX[i] < Gdx.graphics.getWidth() / 18) {
                    enemyX[i] = enemyX[i] + numberOfEnemies * distance;
                    enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                } else {
                    enemyX[i] = enemyX[i] - enemyVelocity;
                }
                batch.draw(bee1, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet1[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);
                batch.draw(bee2, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet2[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);
                batch.draw(bee3, enemyX[i], Gdx.graphics.getHeight() / 2 + enemyOffSet3[i], Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);

                enemyCircles1[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);
                enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);
                enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);
            }

            if (birdY > 0) {
                velocity = velocity + gravity;
                birdY = birdY - velocity;
            } else {
                gameState = 2;
            }

        } else if (gameState == 0) {
            if (Gdx.input.isTouched()) {
                gameState = 1;
            }
        } else if (gameState == 2) {

            font2.draw(batch,"Game Over! Tap To Play Again!", Gdx.graphics.getWidth()/2-400, Gdx.graphics.getHeight()/2);


            if (Gdx.input.isTouched()) {
                gameState = 1;

                birdY = Gdx.graphics.getHeight() / 3;

                for (int i = 0; i < numberOfEnemies; i++) {
                    enemyOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
                    enemyX[i] = Gdx.graphics.getWidth() - bee1.getHeight() / 2 + i * distance;


                    enemyCircles1[i] = new Circle();
                    enemyCircles2[i] = new Circle();
                    enemyCircles3[i] = new Circle();
                }

                velocity = 0;
                scoreEnemy = 0;
                score = 0;

            }

        }

        //4. ve 5. parametre direk sayı girmekten se diğer büyük pikselli yada küçük uyum sağlasın diye verildi
        batch.draw(bird, birdX, birdY, Gdx.graphics.getWidth() / 18, Gdx.graphics.getHeight() / 10);


        //font yazdırmakscore
        font.draw(batch, String.valueOf(score), 100, 100);

        //batch bitirmek
        batch.end();
        //şekli çizdirdik ama görünmez ondna dolayı renkli yapıcaz
        //kuş0dan değilde kendisnin merkezindne başlamas için Gdx.graphics.getWidth() / 18 eklendi
        birdCircle.set(birdX + Gdx.graphics.getWidth() / 36, birdY + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);
        //şekli çizdiriyoruz rengini
        //başlatıp içini dolduruyoruz
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //rengini verdik
//        shapeRenderer.setColor(Color.BLACK);
        //son işlemi
//        shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);


        for (int i = 0; i < numberOfEnemies; i++) {
            //düşmanların şekillerinide ortaya çıkardık
//            shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 36, Gdx.graphics.getHeight() / 2 + enemyOffSet1[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);
//            shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 36, Gdx.graphics.getHeight() / 2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);
//            shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 36, Gdx.graphics.getHeight() / 2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 36);

            if (Intersector.overlaps(birdCircle, enemyCircles1[i]) || Intersector.overlaps(birdCircle, enemyCircles2[i]) || Intersector.overlaps(birdCircle, enemyCircles3[i])) {
//                System.out.println("çarptıııııııııı");
                gameState = 2;
            }
        }


//        shapeRenderer.end();

    }

    @Override
    public void dispose() {

    }
}
