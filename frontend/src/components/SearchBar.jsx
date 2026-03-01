export default function SearchBar({ value, onChange }) {
  return <input className="w-full border rounded-lg px-4 py-2" placeholder="Search products" value={value} onChange={(e) => onChange(e.target.value)} />;
}
