import cocktaildb
import pickle


# for i in range(0, 2000):
#     toApendd = cocktaildb.search_ingredient_by_id(i)
#     if toApendd is not None:
#         ingredients_data.append(toApendd)
#     print(i)
#
# pickle.dump(ingredients_data, open("ingredient_data.p", "wb"))
#
# drink_data = []
# for i in range(10000, 20000):
#     toApend = cocktaildb.search_by_id(i)
#     if toApend is not None:
#         drink_data.append(toApend)
#     print(i)
#
# pickle.dump(drink_data, open("drink_data.p", "wb"))
#
def get_ingredient_id(str, list):
    if str is None:
        return "-20"
    for line in list:
        if line[1].lower() == str.lower():
            return line[0]
    return "617"


drink_data = pickle.load(open("drink_data.p", "rb"))
ingredients_data = pickle.load(open("ingredient_data.p", "rb"))
ingredients = []
for line in ingredients_data:
    if line.strDescription is None:
        desc = "Doesn't exist"
    else:
        desc = line.strDescription
    if line.strAlcohol == "No":
        ingredients.append((int(line.idIngredient), line.strIngredient, 0, 0))
    else:
        if line.strABV is None:
            ingredients.append((int(line.idIngredient), line.strIngredient, 1, 0))
        else:
            ingredients.append((int(line.idIngredient), line.strIngredient, 1, line.strABV))

# [489] to index jegermaistera
ingredients.append((617, "jagermeister", 1, 35))
drinks = []
drink_ingridiant = []
for line in drink_data:
    if line.strAlcoholic == "Alcoholic":
        drinks.append((int(line.idDrink), line.strDrink, 1,
                       line.strInstructions))
    else:
        drinks.append((int(line.idDrink), line.strDrink, 0,
                       line.strInstructions))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient1, ingredients)), line.strMeasure1))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient2, ingredients)), line.strMeasure2))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient3, ingredients)), line.strMeasure3))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient4, ingredients)), line.strMeasure4))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient5, ingredients)), line.strMeasure5))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient6, ingredients)), line.strMeasure6))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient7, ingredients)), line.strMeasure7))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient8, ingredients)), line.strMeasure8))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient9, ingredients)), line.strMeasure9))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient10, ingredients)), line.strMeasure10))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient11, ingredients)), line.strMeasure11))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient12, ingredients)), line.strMeasure12))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient13, ingredients)), line.strMeasure13))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient14, ingredients)), line.strMeasure14))
    drink_ingridiant.append((int(line.idDrink), int(get_ingredient_id(line.strIngredient15, ingredients)), line.strMeasure15))



filted_drink_ing = list(filter(lambda tup: tup[1] != -20, drink_ingridiant))
filted_drink_ing = [(v[0], v[1], "") if v[2] is None else v for v in filted_drink_ing]
for i in range(len(filted_drink_ing)):
    temp_list = list(filted_drink_ing[i])
    temp_list[2] = temp_list[2].strip()
    filted_drink_ing[i] = tuple(temp_list)

for line in filted_drink_ing:
    print(line)


pickle.dump(drinks, open("fil_drink.p", "wb"))
pickle.dump(ingredients, open("fil_ingredients.p", "wb"))
pickle.dump(filted_drink_ing, open("fil_drink_ing.p", "wb"))