import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <header className="bg-green-600 text-white sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 py-3 flex justify-between items-center">
        <Link to="/" className="font-bold text-xl">Blinkit Clone</Link>
        <nav className="flex gap-4 text-sm">
          <Link to="/orders">Orders</Link>
          <Link to="/cart">Cart</Link>
          <Link to="/profile">Profile</Link>
          <Link to="/admin">Admin</Link>
          <Link to="/delivery">Delivery</Link>
        </nav>
      </div>
    </header>
  );
}
