import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Link, useNavigate } from "react-router-dom";
import { logoutUser } from "../../Slices/UserSlice";
import { AppDispatch, RootState } from "../../Store";

export const ManagerNavbar: React.FC = () => {
    const dispatch:AppDispatch = useDispatch();
    const navigator = useNavigate();

    const handleLogout = () => {
        dispatch(logoutUser());
        navigator("/login");
    }

    const user = useSelector((state:RootState) => state.user.user);

    return(
        <nav className="navbar">
            <ul className="nav-menu">
                <li className="logo">
                    <img src="/revBackground.png"></img>
                </li>
                <li className="nav-item">
                    <Link to={"/homepage"} className="nav-link">Home</Link>
                </li>
                <li className="nav-item">
                    <Link to={"/profile"} className="nav-link">Profile</Link>
                </li>
                <li className="nav-item">
                    <Link to={"/accounts"} className="nav-link">Accounts</Link>
                </li>
                <li className="nav-item">
                    <Link to={"/customers"} className="nav-link">Customers</Link>
                </li>
                <li className="logout">
                    <Link to={"/login"} className="nav-link">
                        <button className="logout-btn" onClick={handleLogout}>Logout</button>
                    </Link>
                </li>
            </ul>
        </nav>
    )

}