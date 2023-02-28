from flask import Flask, render_template, request, redirect, session, jsonify
from DBConnection import Db
import datetime
app = Flask(__name__)
app.secret_key="block"


# ===================================================admin=================================================================

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
