CREATE TABLE IF NOT EXISTS ingredients (
  id INTEGER NOT NULL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  description TEXT,
  isAlcoholic INTEGER NOT NULL CHECK(isAlcoholic IN (0, 1)),
  strABV INTEGER
);

CREATE TABLE IF NOT EXISTS drinks (
  id INTEGER NOT NULL PRIMARY KEY,
  name TEXT NOT NULL UNIQUE,
  isAlcoholic INTEGER NOT NULL CHECK(isAlcoholic IN (0, 1)),
  instructions TEXT
);

CREATE TABLE IF NOT EXISTS drinks_ingredients (
  drink_id INTEGER NOT NULL,
  ingredient_id INTEGER NOT NULL,
  PRIMARY KEY (drink_id, ingredient_id),
  FOREIGN KEY (drink_id) REFERENCES drinks(id),
  FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);
