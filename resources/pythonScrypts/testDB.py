import sqlite3


def select_all_table(conn, table):
    cur = conn.cursor()
    cur.execute("SELECT * FROM " + table)

    rows = cur.fetchall()

    for row in rows:
        print(row)


conn = sqlite3.connect('database.db')

select_all_table(conn, "drinks")
