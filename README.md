# Inventory Management App

A Java/JavaFX that manages inventory and information for a sample company's parts and products.

## Description

The app loads with a hard-coded list of parts and products. The user may add, modify, or delete any part or product. "Stock" is how many the company currently has, and "min/max" is how much the company would like to keep on hand; other fields are self-explanatory. Products are what the company makes and sells using many parts. To modify this list, click on a product and the modify button. Two lists appear on the right-hand side: the top list is all parts available, the bottom is the parts currently used for that product. Users may add or delete parts for the product as they wish. Note that while all user changes will be reflected so long as the app is running, there is no persistence to this application per WGU's specifications.

## Getting Started

### Dependencies

* Java SDK
* JavaFX
* Ant
* Visual Studio

### Installing

* Place files/folders in the desired directory
* Install dependencies as needed
* Ensure the paths line up as specified in the build.xml and .vscode/settings.json files

### Executing program

* Open a terminal to the root directory
* Run the three tasks in the .vscode/tasks.json file: "ant compile", "ant jar", "ant run-jar"

## Help

* Feel free to email me at wess.lancaster@gmail.com

## Author

Wess Lancaster  
[LinkedIn](https://linkedin.com/in/wessbl)