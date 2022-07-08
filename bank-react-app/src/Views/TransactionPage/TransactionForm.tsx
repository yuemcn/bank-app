import React, { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { Navbar } from "../../Components/Navbar/Navbar";
import { credit, debit } from "../../Slices/TransactionSlice";
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

        console.log("handleSubmit type: " + type);

        if (type === "credit") {
            let info = {
                accountNumber: accounts.current?.accountNumber!,
                amount: amount,
                description: description
            }
            console.log(info);
            dispatch(credit(info));
        }
        else if (type === "debit") {
            let info = {
                accountNumber: accounts.current?.accountNumber!,
                amount: amount,
                description: description
            }
            console.log(info);
            dispatch(debit(info));
        }
        else {
            let info = {
                source: transactions.current?.account?.accountNumber,
                destination: destination,
                amount: amount
            }
            console.log(info);
        }
        navigator("/accounts");
    }

    return (
        <div>
            <Navbar />
            <div className="header">
                <h1>New Transaction</h1>
            </div>
            <form>
                <label>
                    Type: 
                    <select className="type" id="type" onChange={handleSelect}>
                        <option selected value="select-placeholder">-- Select Type --</option>
                        <option value="credit">Deposit</option>
                        <option value="debit">Withdraw</option>
                        <option value="transfer">Transfer</option>
                    </select>
                </label>
                <br />
                <label>
                    Amount: 
                    <input
                        className="transaction-input"
                        type="number"
                        name="amount"
                        onChange={handleChange}
                    />
                </label>
                <br />
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