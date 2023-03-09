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


# ===================================================manifacture===========================================================


@app.route('/product_view', methods=['post','get'])
def product_view():
    if session['lg'] == "lin":
        data=[]
        with open(compiled_contract_path) as file:
            contract_json = json.load(file)  # load contract info as JSON
            contract_abi = contract_json['abi']  # fetch contract's abi - necessary to call its functions
        contract = web3.eth.contract(address=deployed_contract_address, abi=contract_abi)
        blocknumber = web3.eth.get_block_number()
        #print(blocknumber)
        for i in range(blocknumber, 0, -1):
            a = web3.eth.get_transaction_by_block(i, 0)
            decoded_input = contract.decode_function_input(a['input'])
            # print(decoded_input)
            res={}
            res['productname']=decoded_input[1]['p']
            res['location']=decoded_input[1]['l']
            res['timestamp']=decoded_input[1]['t']
            res['ins']=decoded_input[1]['ins']
            res['details']=decoded_input[1]['d']
            res['mid']=decoded_input[1]['mid']
            if str(decoded_input[1]['mid'])==str(session['lid']):
                data.append(res)

        return render_template("Manufacture/product_view.html", data=data)
    else:
        return redirect('/')




if __name__ == '__main__':
    app.run(host="0.0.0.0")
