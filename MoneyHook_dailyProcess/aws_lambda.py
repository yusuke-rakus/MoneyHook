import pymysql.cursors
import datetime
import calendar

con = pymysql.connect(host="Host",
                      port=3306,
                      user="User",
                      password="Password",
                      database="Database",
                      cursorclass=pymysql.cursors.DictCursor)


today = datetime.date.today()
_, last_day = calendar.monthrange(today.year, today.month)


def run_search_insert():
    with con:
        with con.cursor() as cursor:
            if last_day == today.day:
                sql = f'SELECT monthly_transaction_id,user_no, monthly_transaction_name, monthly_transaction_amount, monthly_transaction_date, category_id, sub_category_id FROM monthly_transaction WHERE {today.day} <= monthly_transaction_date AND include_flg = TRUE;'
            else:
                sql = f'SELECT monthly_transaction_id,user_no, monthly_transaction_name, monthly_transaction_amount, monthly_transaction_date, category_id, sub_category_id FROM monthly_transaction WHERE monthly_transaction_date = {today.day} AND include_flg = TRUE;'
            cursor.execute(sql)
            result = cursor.fetchall()

            insert_sql = "INSERT INTO transaction(user_no, transaction_name, transaction_amount, transaction_date, category_id, sub_category_id, fixed_flg) VALUES(%s, %s, %s, %s, %s, %s, TRUE);"

            val = []
            for mt in result:
                val.append(
                    [mt["user_no"], mt["monthly_transaction_name"], mt["monthly_transaction_amount"], today.__str__(),
                     mt[
                         "category_id"], mt["sub_category_id"]])

            cursor.executemany(insert_sql, val)
        con.commit()
        con.close()


run_search_insert()
