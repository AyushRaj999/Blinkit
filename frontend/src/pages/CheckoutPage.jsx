import { useNavigate } from 'react-router-dom';
import api from '../services/api';

export default function CheckoutPage() {
  const nav = useNavigate();
  const place = async () => {
    await api.post('/api/orders');
    nav('/orders');
  };
  return <div className="max-w-3xl mx-auto p-4"><h2 className="text-2xl font-bold">Checkout</h2><button className="mt-4 bg-green-600 text-white px-4 py-2 rounded" onClick={place}>Place Order</button></div>;
}
