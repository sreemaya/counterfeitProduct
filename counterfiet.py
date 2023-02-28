from flask import Flask, render_template, request, redirect, session, jsonify
from DBConnection import Db
import datetime
app = Flask(__name__)
app.secret_key="block"


# ===================================================admin=================================================================





@app.route('/view_com')
def view_com():
    if session['lg'] == "lin":
        db=Db()
        q=db.select("select * from user,spam where spam.uid=user.user_id")
        return render_template("Admin/View_com.html",data=q)
    else:
        return redirect('/')


@app.route('/view_comp')
def view_comp():
    if session['lg'] == "lin":
        db = Db()
        q = db.select("select * from user,complaint where complaint.user_id=user.user_id")
        print(q)
        return render_template("Admin/View_complaints.html", data=q)
    else:
        return redirect('/')


if __name__ == '__main__':
    app.run(host="0.0.0.0")
