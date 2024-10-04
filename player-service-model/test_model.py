from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/predict', methods=['POST'])
def predict():
    # Your ML model prediction logic
    data = request.get_json()
    result = {"prediction": "some_value"}  # Dummy prediction
    return jsonify(result)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)