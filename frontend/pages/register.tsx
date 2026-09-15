import { useState } from "react";
import confetti from "@hiseb/confetti";

type User = {
  username: string;
};

const registerUser = async (username: string, password: string) => {
  const route = '/api/v1/register'
  const response = await fetch(route, {
    method: 'post',
    headers: {
      'username': username,
      'password': password
    }
  });
  const data = await response.json();
  console.log(data)

  localStorage.setItem("token", data.token)
    if (data.status = 200) {
        confetti();
    } else {
        alert("ERROR: " + data.status + data.body)
        console.log("ERROR")
    }

}
export default function Register() {
  const [users, setUsers] = useState<User[]>([]);

  const getAllUsers = async () => {
    const response = await fetch('/api/testing/security/allUsers');

    if (!response.ok) {
      alert('Users could not be loaded');
      return;
    }

    setUsers(await response.json());
  };

  return (
    <div>
        <h1>Register</h1>
        <input
        id="username"
        type="username"
        className="text-white font-mono border"></input>
        <input
        id="password"
        type="text"
        className="text-white font-mono border"></input>
        <button
          onClick={() => {
            const username = (document.getElementById('username') as HTMLInputElement).value;
            const password = (document.getElementById('password') as HTMLInputElement).value;

            registerUser(username, password);

          }}
        >
          REGISTER
        </button>
    </div>
  )

}
