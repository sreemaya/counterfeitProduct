from flask import Flask, render_template, request, redirect, session, jsonify
from DBConnection import Db
import datetime
app = Flask(__name__)
app.secret_key="block"

@app.route('/',methods=['POST','get'])
def login1():
    if request.method=='POST':
        username = request.form['username']
        password = request.form['password']
        db=Db()
        res = db.selectOne("select * from login where user_name = '" + username + "' and password = '" + password + "'")
        if res is not None:
            session['lg'] = "lin"
            session['lid']=res['login_id']
            if res['user_type']=='admin':
                 return redirect('/ahome')
            if res['user_type']=='manufacturer':
                 return redirect('/mhome')
            if res['user_type']=='pending':
                return ''' <script> alert("Please wait for verification...!!!!");window.location='/' </script>   '''
        else:
            return ''' <script> alert("Invalid Username  or Password...!!!!");window.location='/' </script>   '''


    return render_template("login.html")

@app.route('/mhome')
def mhome():
    if session['lg'] == "lin":
        return render_template("Manufacture/index.html")
    else:
        return redirect('/')


@app.route('/logout')
def logout():
    session['lg']=""
    session.clear()
    return redirect('/')

# ===================================================admin=================================================================
@app.route('/ahome')
def ahome():
    if session['lg'] == "lin":
        return render_template("Admin/index.html")
    else:
        return redirect('/')
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


@app.route('/view_com')
def view_com():
    if session['lg'] == "lin":
        db=Db()
        q=db.select("select * from user,spam where spam.uid=user.user_id")
        return render_template("Admin/View_com.html",data=q)
    else:
        return redirect('/')

@app.route('/approve/<i>')
def approve(i):
    if session['lg'] == "lin":
        db=Db()
        q=db.update("update login set user_type='manufacturer' where login_id='"+i+"'")
        return ''' <script> alert("Approved successfully...!!!!");window.location='/view_man' </script>   '''

    else:
        return redirect('/')
@app.route('/reject/<i>')
def reject(i):
    if session['lg'] == "lin":
        db = Db()
        q = db.delete("delete from login where login_id='" + i + "'")
        q1 = db.delete("delete from company where ins_id='" + i + "'")
        return ''' <script> alert("Rejected successfully...!!!!");window.location='/view_man' </script>   '''

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


@app.route('/reply/<i>',methods=['get','post'])
def reply(i):
    if session['lg'] == "lin":
        if request.method == 'POST':
            db = Db()
            r=request.form['r']
            db.update("update complaint set reply='"+r+"',reply_date=curdate() where complaint_id='"+i+"'")
            return ''' <script> alert("Updated successfully...!!!!");window.location='/view_comp' </script>   '''

        return render_template("Admin/send_reply.html")
    else:
        return redirect('/')


# ===================================================manifacture===========================================================
@app.route('/reg',methods=['post','get'])
def reg():
    if request.method=='POST':
        n1=request.form['n1']
        n2=request.form['n2']
        n3=request.form['n3']
        n4=request.form['n4']
        n5=request.form['n5']
        n6=request.form['n6']
        n7=request.form['n7']
        n8=request.form['n8']
        n9=request.form['n9']
        n10=request.form['n10']
        db=Db()
        q2=db.selectOne("select * from login where user_name='"+n8+"'")
        if q2 is None:
            q=db.insert("insert into `login`(`login_id`,`user_name`,`password`,`user_type`) values ( NULL,'"+n8+"','"+n10+"','pending');")
            q1=db.insert("insert into `company`(`ins_id`,`ins_name`,`ins_license`,`ins_establishedyr`,`ins_place`,`ins_post`,`ins_pin`,`ins_district`,`ins_ph`,`ins_email`) values ( '"+str(q)+"','"+n1+"','"+n2+"','"+n3+"','"+n4+"','"+n5+"','"+n7+"','"+n6+"','"+n9+"','"+n8+"');")
            return ''' <script> alert("Registered sucessfully...!!!!");window.location='/' </script>   '''

        else:
            return ''' <script> alert("USer already found...!!!!");window.location='/reg' </script>   '''

    return render_template("Manufacture/reg.html")

if __name__ == '__main__':
    app.run(host="0.0.0.0")
