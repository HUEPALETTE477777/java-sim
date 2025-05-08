package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import java.io.InputStream;
import java.io.InputStreamReader;

import main.GamePanel;

public class TileHandler {
    GamePanel gamePanel;
    public ArrayList<Tile> tile;
    public int mapTileNum[][];

    public List<int[]> mapDimensionList = new ArrayList<>(); // WILL BE USED TO RENDER THE MAP DIMENSION FOR PERFORMANCE

    public TileHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        tile = new ArrayList<>();
        mapTileNum = new int[gamePanel.maxWorldRow][gamePanel.maxWorldCol]; // THE ACTUAL MAX CAP FOR A MAP, USED GLOBALLY!

        fetchTileImage();
        loadMap("/images/maps/map01.txt");
    }

    public void fetchTileImage() {
        try {
            Tile tile1 = new Tile();
            tile1.image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/grass.png"));
            tile.add(tile1);

            Tile tile2 = new Tile();
            tile2.image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/concrete.png"));
            tile2.collision = true;
            tile.add(tile2);

            Tile tile3 = new Tile();
            tile3.image = ImageIO.read(getClass().getResourceAsStream("/images/tiles/water.png"));
            tile3.collision = true;
            tile.add(tile3);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String mapFile) {
        try {
            InputStream mapImport = getClass().getResourceAsStream(mapFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(mapImport));
    
            int row = 0;
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] numbers = line.split(" ");  
                int[] rowLen = new int[numbers.length];

                for (int col = 0; col < numbers.length; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col] = num;  
                }
                
                mapDimensionList.add(rowLen);
                row++;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void draw(Graphics2D g2d) {
        int mapRows = mapDimensionList.size(); 
        
        // GO THROUGH THE NUMBER OF MAP ROWS, SERVING AS WORLD ROW
        for (int worldRow = 0; worldRow < mapRows; worldRow++) {
            // FETCH THE ROW BY INDICE, AND EACH ELEMENT IS A "COL"
            int[] rowData = mapDimensionList.get(worldRow); 
            int mapCols = rowData.length; 
            
            // RENDER EACH TILE AS A 1D ARRAY
            for (int worldCol = 0; worldCol < mapCols; worldCol++) {
                renderTiles(g2d, worldRow, worldCol); 
            }
        }
        
    }
    
    // RENDERS 1 TILE INDIVIDUALLY
    public void renderTiles(Graphics2D g2d, int worldRow, int worldCol) {
        int tileNum = mapTileNum[worldRow][worldCol];

        int worldX = worldCol * gamePanel.tileSize;
        int worldY = worldRow * gamePanel.tileSize;

        // PLAYER SCREEN X/Y IS 576 PIXELS, SCREEN X IS THE TARGET TILE
        int screenX = (worldX - gamePanel.player.worldX);
        int screenY = (worldY - gamePanel.player.worldY);

        // INCLUDE PLAYER OFFSET!
        screenX += gamePanel.player.screenX;
        screenY += gamePanel.player.screenY;

        // CHECK IF TILE CAN BE RENDERED INSIDE PLAYER SCREEN
        boolean screenXBound = (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX)
                && (worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX);
        boolean screenYBound = (worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY)
                && (worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY);
        boolean withinBounds = screenXBound && screenYBound;

        if (withinBounds) {
            g2d.drawImage(tile.get(tileNum).image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

}
