import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Navbar } from "../../Components/Navbar/Navbar";
import { ITransaction } from "../../Interfaces/ITransaction";
import { credit } from "../../Slices/TransactionSlice";
import { AppDispatch, RootState } from "../../Store";

export const TransactionForm: React.FC = () => {

    const transactions = useSelector((state: RootState) => state.transactions);
    const accounts = useSelector((state: RootState) => state.accounts);

    const navigator = useNavigate();
    const dispatch: AppDispatch = useDispatch();

    const [type, setType] = useState<string>("");
    const [amount, setAmount] = useState<number>(0);
    const [destination, setDestination] = useState(0);
    const [description, setDescription] = useState("");

    const handleSelect = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setType(event.currentTarget.value);
        console.log("Type: " + type);
        console.log("event.currentTarget.value: " + event.currentTarget.value);
    }

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.name === "amount") {
            setAmount(event.target.valueAsNumber);
        }
        else if (event.target.name === "destination") {
            setDestination(event.target.valueAsNumber);
        }
    }

    const handleSubmit = (event: React.FormEvent) => {
        event.preventDefault();
        // type is credit or debit
        if (type === "credit" || type === "debit") {
            let info = {
                accountNumber: accounts.currentAccount?.accountNumber!,
                amount: amount,
                description: description
            }
            dispatch(credit(info));
        }
        else {
            let info = {
                source: transactions.current?.account?.accountNumber,
                destination: destination,
                amount: amount
            }
        }
        navigator("/accounts");
    }

    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>New Transaction</h1>
                <br />
                <h2>Transaction Type</h2>
            </div>
            <form>
                <select className="type" id="type" onChange={handleSelect}>
                    <option selected value="credit">Deposit</option>
                    <option value="debit">Withdraw</option>
                    <option value="transfer">Transfer</option>
                </select>
                <br/>
                {/* <label>
                    Account:
                    <input
                        className="transaction-input"
                        type="text"
                        name="account"
                        onChange={handleChange}
                    />
                </label> */}
                <br/>
                <label>
                    Amount:
                    <input
                        className="transaction-input"
                        type="number"
                        name="amount"
                        onChange={handleChange}
                    />
                </label>
                <br/>
                <>
                {transactions.current?.type === "transfer" ? () => {
                    <label>
                        Destination:
                        <input
                            className="transaction-input"
                            type="number"
                            name="destination"
                            onChange={handleChange}
                        />
                    </label>
                } :
                    <></>
                }
                </>

            </form>
            <button className="submit-button" onClick={handleSubmit}>Submit</button>
        </div>
    )

    // return (
    //     <div>
    //         <Navbar />
    //         <div className="header">
    //             <h1>New Transaction</h1>
    //             <br/>
    //             <h2>Transaction Type</h2>
    //         </div>
    //         <form className="transaction-type-form">
    //             <label>
    //                 <input
    //                     className="radio-box"
    //                     type="radio"
    //                     name="transaction-type"
    //                     value="credit"
    //                     onChange={handleInput}
    //                 />
    //                 Deposit
    //             </label>
    //             <br/>
    //             <label>
    //                 <input
    //                     className="radio-box"
    //                     type="radio"
    //                     name="transaction-type"
    //                     value="debit"
    //                     onChange={handleInput}
    //                 />
    //                 Withdraw
    //             </label>
    //             <br/>
    //             <label>
    //                 <input
    //                     className="radio-box"
    //                     type="radio"
    //                     name="transaction-type"
    //                     value="transfer"
    //                     onChange={handleInput}
    //                 />
    //                 Transfer
    //             </label>
    //         </form>
    //         <button className="submit-button" onClick={handleSubmit}>Submit</button>
    //     </div>
    // )

}