from PIL import Image

tileSize = 32
targetSize = 2
tileRatio = tileSize / targetSize

original = Image.open("images/cyber_tileset.png")
result = Image.new("RGB", (original.width / tileRatio, original.height / tileRatio), "black")

pixels = original.load()
resultPixels = result.load()

for yTile in range(0, original.height, tileSize):
	for xTile in range(0, original.width, tileSize):
		for quarterY in range(0, tileSize, tileRatio):
			for quarterX in range(0, tileSize, tileRatio):
				r = 0
				g = 0
				b = 0
				counter = 0
				for y in range(yTile + quarterY, yTile + quarterY + tileRatio):
					for x in range(xTile + quarterX, xTile + quarterX + tileRatio):
						currPixel = pixels[x, y]
						if currPixel[3] > 250:
							r += currPixel[0]
							g += currPixel[1]
							b += currPixel[2]
							counter += 1
				if (counter > 0):
					rAvg = r/counter
					gAvg = g/counter
					bAvg = b/counter
					resultPixels[xTile / tileRatio + quarterX / tileRatio, yTile / tileRatio + quarterY / tileRatio] = (rAvg, gAvg, bAvg)
				else:
					resultPixels[xTile / tileRatio + quarterX / tileRatio, yTile / tileRatio + quarterY / tileRatio] = (0, 0, 0, 255)

result.save("images/cyber_minimap.png")
