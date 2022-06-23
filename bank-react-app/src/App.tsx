import React from 'react';
import { BrowserRouter, HashRouter, Navigate, Route, Routes } from 'react-router-dom';
import './App.css';
import { RegisterForm } from './Components/RegisterForm/RegisterForm';
import { AccountsPage } from './Views/AccountsPage/AccountsPage';
import { AllAccountsPage } from './Views/AllAccountsPage/AllAccountsPage';
import { CustomersPage } from './Views/CustomersPage/CustomersPage';
import { Homepage } from './Views/Homepage/Homepage';
import {LoginPage} from './Views/LoginPage/LoginPage';
import { EditProfilePage } from './Views/ProfilePage/EditProfilePage';
import { ProfilePage } from './Views/ProfilePage/ProfilePage';
import { RegisterPage } from './Views/RegisterPage/RegisterPage';

function App() {
  return (
    <BrowserRouter>
      <Routes >
        <Route path="*" element={<Navigate to="/login" replace />}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path="/homepage" element={<Homepage />}/>
        <Route path="/profile" element={<ProfilePage />}/>
        <Route path="/accounts" element={<AccountsPage />}/>
        <Route path="/register" element={<RegisterPage />}/>
        <Route path="/customers" element={<CustomersPage />}/>
        <Route path="/all-accounts" element={<AllAccountsPage />}/>
        <Route path="/edit-profile" element={<EditProfilePage />}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
