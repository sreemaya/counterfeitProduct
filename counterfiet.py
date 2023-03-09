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



# ===================================================manifacture===========================================================

@app.route('/product_add',methods=['post','get'])
def product_add():
    if session['lg'] == "lin":
        if request.method=='POST':
            n1=request.form['n1']
            n2=request.form['n2']
            n3=request.form['n3']
            n4=request.form['n4']
            n5=request.form['n5']
            with open(compiled_contract_path) as file:
                contract_json = json.load(file)  # load contract info as JSON
                contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
            c=0
            contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
            blocknumber = web3.eth.get_block_number()
            for i in range(blocknumber, 1, -1):
                a = web3.eth.get_transaction_by_block(i, 0)
                decoded_input = contract.decode_function_input(a['input'])
                if str(decoded_input[1]['p'])==n1 and str(decoded_input[1]['l'])==n2 and str(decoded_input[1]['t'])==n3 and str(decoded_input[1]['ins'])==n4 and str(decoded_input[1]['d'])==n5:
                    c+=1
                else:
                    c=c
            if c == 0:
                message2 = contract.functions.addEmployee(blocknumber + 1,n1,n2,n3,n4,n5,(session['lid'])).transact()
                # Add data
                return ''' <script> alert("Inserted...!!!!");window.location="/product_add" </script>   '''
            else:
                return ''' <script> alert("Already Inserted...!!!!");window.location="/product_add" </script>   '''

        return render_template("Manufacture/product_add.html")
    else:
        return redirect('/')


if __name__ == '__main__':
    app.run(host="0.0.0.0")
