CREATE TABLE drinks (
  id INTEGER NOT NULL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  isAlcoholic BOOLEAN NOT NULL CHECK(isAlcoholic IN (0, 1)),
  instructions TEXT
);

CREATE TABLE ingredients (
  id INTEGER NOT NULL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  isAlcoholic BOOLEAN NOT NULL CHECK(isAlcoholic IN (0, 1)),
  strABV INTEGER
);

CREATE TABLE drinks_ingredients (
  drinks_ingredients_id INTEGER PRIMARY KEY,
  drinkId INTEGER NOT NULL,
  ingredientId INTEGER NOT NULL,
  ingredient_measure TEXT NOT NULL,
  FOREIGN KEY (drinkId) REFERENCES drinks(id),
  FOREIGN KEY (ingredientId) REFERENCES ingredients(id)
);

CREATE TABLE favourites (
	drinkId INTEGER NOT NULL PRIMARY KEY,
	FOREIGN KEY (drinkId) REFERENCES drinks(id)
);


