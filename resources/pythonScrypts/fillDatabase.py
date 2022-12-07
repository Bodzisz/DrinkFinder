import sqlite3
import pickle

con = sqlite3.connect("database.db")
cur = con.cursor()
print(cur.execute("PRAGMA table_info('ingredients')").fetchall())
print(cur.execute("PRAGMA table_info('drinks')").fetchall())
print(cur.execute("PRAGMA table_info('drinks_ingredients')").fetchall())
drinks = pickle.load(open("fil_drink.p", "rb"))

ingredients = pickle.load(open("fil_ingredients.p", "rb"))
d_i = pickle.load(open("fil_drink_ing.p", "rb"))

cur.executemany("INSERT INTO drinks VALUES(?, ?, ?, ?)", drinks)
cur.executemany("INSERT INTO ingredients VALUES(?, ?, ?, ?)", ingredients)

for i in range(len(d_i)):
    d_i[i] = (i,) + d_i[i]

cur.executemany("INSERT INTO drinks_ingredients VALUES(?, ?, ?, ?)", d_i)
con.commit()
