import { Link } from 'react-router-dom';

export default function ProductCard({ product, onAdd }) {
  return (
    <div className="bg-white rounded-lg border p-4 shadow-sm">
      <img src={product.imageUrl || 'https://placehold.co/400x240'} alt={product.name} className="w-full h-40 object-cover rounded" />
      <h3 className="font-semibold mt-2">{product.name}</h3>
      <p className="text-green-700 font-bold">₹{product.price}</p>
      <div className="mt-2 flex gap-2">
        <button onClick={() => onAdd(product.id)} className="bg-green-600 text-white px-3 py-1 rounded">Add</button>
        <Link to={`/products/${product.id}`} className="border px-3 py-1 rounded">View</Link>
      </div>
    </div>
  );
}
