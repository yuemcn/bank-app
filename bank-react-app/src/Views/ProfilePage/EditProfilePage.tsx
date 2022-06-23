import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { Navbar } from "../../Components/Navbar/Navbar";
import { IUser } from "../../Interfaces/IUser";
import { updateUser } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";

import "./EditProfilePage.css";

export const EditProfilePage: React.FC = () => {

    const profile = useSelector((state: RootState) => state.user);
    const dispatch: AppDispatch = useDispatch();
    const navigator = useNavigate();

    const [firstname, setFirstname] = useState<string>("");
    const [lastname, setLastname] = useState<string>("");
    const [ssn, setSSN] = useState<number>(0);
    const [email, setEmail] = useState<string>("");
    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    useEffect(() => {
        console.log(profile.user);
        setFirstname(profile.user?.firstname!);
        setLastname(profile.user?.lastname!);
        setSSN(profile.user?.ssn!);
        setEmail(profile.user?.email!);
        setUsername(profile.user?.username!);
        setPassword(profile.user?.password!);
    }, []);

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.name === "first-name-field") {
            setFirstname(event.target.value);
        }
        else if (event.target.name === "last-name-field") {
            setLastname(event.target.value);
        }
        else if (event.target.name === "email-field") {
            setEmail(event.target.value);
        }
        else if (event.target.name === "username-field") {
            setUsername(event.target.value);
        }
        else {
            setPassword(event.target.value);
        }
    }

    const handleSubmit = (event: React.MouseEvent<HTMLButtonElement>) => {
        let user: IUser = {
            firstname: firstname,
            lastname: lastname,
            ssn: ssn,
            email: email,
            username: username,
            password: password,
            type: profile.user?.type!,
            accounts: profile.user?.accounts
        }
        console.log(user);
        dispatch(updateUser(user));
        // alert("Profile information updated");
        navigator("/profile");
    }

    return (
        <div className="edit-profile">
            <Navbar />
            <form className="edit-profile-form">
                <div className="input-div">
                    <h4 className="input-h4">First Name</h4>
                    <input
                        className="registration-input"
                        type="text"
                        name="first-name-field"
                        // placeholder={profile.user?.firstname}
                        // value={profile.user?.firstname}
                        defaultValue={profile.user?.firstname}
                        onChange={handleChange}
                    />
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Last Name</h4>
                    <input
                        className="registration-input"
                        type="text"
                        name="last-name-field"
                        // placeholder={profile.user?.lastname}
                        // value={profile.user?.lastname}
                        defaultValue={profile.user?.lastname}
                        onChange={handleChange}
                    />
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Email</h4>
                    <input
                        className="registration-input"
                        type="text"
                        name="email-field"
                        // placeholder={profile.user?.email}
                        // value={profile.user?.email}
                        defaultValue={profile.user?.email}
                        onChange={handleChange}
                    />
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Username</h4>
                    <input
                        className="registration-input"
                        type="text"
                        name="username-field"
                        // placeholder={profile.user?.username}
                        // value={profile.user?.username}
                        defaultValue={profile.user?.username}
                        onChange={handleChange}
                    />
                </div>
                <div className="input-div">
                    <h4 className="input-h4">Password</h4>
                    <input
                        className="registration-input"
                        type="password"
                        name="password-field"
                        // placeholder={profile.user?.password}
                        // value={profile.user?.password}
                        defaultValue={profile.user?.password}
                        onChange={handleChange}
                        required
                    />
                </div>
            </form>
            <br/>
            {/* <input className="submit-button" type="submit" value="Submit"/> */}
            <button className="submit-button" onClick={handleSubmit}>Submit</button>
            <Link to="/profile">Cancel</Link>
        </div>
    )
}