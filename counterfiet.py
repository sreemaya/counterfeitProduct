from flask import Flask, render_template, request, redirect, session, jsonify
from DBConnection import Db
import datetime
app = Flask(__name__)
app.secret_key="block"


# ===================================================admin=================================================================

@app.route('/view_man')
def view_man():
    if session['lg'] == "lin":
        db=Db()
        q=db.select("select * from login,company where ins_id=login_id and user_type='pending'")
        return render_template("Admin/View_manufacture.html",data=q)
    else:
        return redirect('/')
@app.route('/view_mans')
def view_mans():
    if session['lg'] == "lin":
        db=Db()
        q=db.select("select * from login,company where ins_id=login_id and user_type='manufacturer'")
        return render_template("Admin/View_manufacture.html",data=q)
    else:
        return redirect('/')

@app.route('/view_user')
def view_user():
    if session['lg'] == "lin":
        db=Db()
        q = db.select("select * from user")
        return render_template("Admin/View_user.html", data=q)
    else:
        return redirect('/')






if __name__ == '__main__':
    app.run(host="0.0.0.0")
